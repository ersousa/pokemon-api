package com.pokemon.pokemonapi.service.impl;

import com.pokemon.pokemonapi.domain.*;
import com.pokemon.pokemonapi.domain.dto.EvolutionaryLineDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import com.pokemon.pokemonapi.service.PokemonService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.logging.Logger;

@Service
@Slf4j
@AllArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final WebClient webClient;
    private static final String URI_POKEMON = "/pokemon/{name}";
    private static final String URI_POKEMON_SPECIES = "/pokemon-species/{name}";

    private static final Map<String, PokemonDTO> POKEMON_CACHE = new HashMap<>();

    @Override
    @CircuitBreaker(name = "getPokemonByNameCircuitBreak", fallbackMethod = "getPokemonByNameFallBack")
    public ResponseEntity<PokemonDTO> getByName(String name) {
        log.info("Searching infos for Pokemon {}", name);
        Pokemon pokemon =
                webClient
                        .get()
                        .uri(URI_POKEMON, name)
                        .retrieve()
                        .bodyToMono(Pokemon.class).block();
        if(pokemon != null){
            cacheFeeding(name, pokemon);
        }
        return new ResponseEntity<>(PokemonDTO.getFromEntity(pokemon), HttpStatus.OK);
    }

    private ResponseEntity<PokemonDTO> getPokemonByNameFallBack(String name, Throwable exception){
        log.info("Searching {} in cache", name);
        PokemonDTO pokemonDTO = POKEMON_CACHE.get(name);
        if(Objects.nonNull(pokemonDTO)){
            return new ResponseEntity<>(pokemonDTO, HttpStatus.OK);
        }

        log.info("Nothing found in cache :(");
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    private void cacheFeeding(String name, Pokemon pokemon) {
        log.info("Feeding cache . . .");
        POKEMON_CACHE.put(name, PokemonDTO.getFromEntity(pokemon));
    }

    @Override
    public ResponseEntity<EvolutionaryLineDTO> getEvolutionLineByName(String name) {
        log.info("Searching evolutionary line for {}", name);
        EvolutionaryLineDTO evolutionaryLineDTO = new EvolutionaryLineDTO();
        List<String> forms = new ArrayList<>();
        EvolutionaryLine evolutionaryLine = null;

        PokemonSpecie pokemonSpecie = getSpecie(name);

        if (Objects.nonNull(pokemonSpecie) && Objects.nonNull(pokemonSpecie.getEvolutionChain())) {
            evolutionaryLine = getEvolutionaryLine(pokemonSpecie);
        }

        if (Objects.nonNull(evolutionaryLine) && Objects.nonNull(evolutionaryLine.getChain())
                && Objects.nonNull(evolutionaryLine.getChain().getEvolvesTo())) {
            forms = evolutionaryLine.getChain().getEvolvesTo().stream()
                    .map(Evolution::getSpecies)
                    .map(Specie::getName).toList();
        }

        evolutionaryLineDTO.setForms(forms);
        return new ResponseEntity<>(evolutionaryLineDTO, HttpStatus.OK);
    }

    private EvolutionaryLine getEvolutionaryLine(PokemonSpecie pokemonSpecie) {
        return webClient
                .get()
                .uri(pokemonSpecie.getEvolutionChain().getUrl())
                .retrieve()
                .bodyToMono(EvolutionaryLine.class).block();
    }

    private PokemonSpecie getSpecie(String name) {
        return webClient
                .get()
                .uri(URI_POKEMON_SPECIES, name)
                .retrieve()
                .bodyToMono(PokemonSpecie.class).block();
    }
}
