package com.pokemon.pokemonapi.service.impl;

import com.pokemon.pokemonapi.domain.*;
import com.pokemon.pokemonapi.domain.dto.EvolutionaryLineDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import com.pokemon.pokemonapi.service.PokemonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@Slf4j
@AllArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final WebClient webClient;
    private static final String URI_POKEMON = "/pokemon/{name}";
    private static final String URI_POKEMON_SPECIES = "/pokemon-species/{name}";

    @Override
    public ResponseEntity<PokemonDTO> getByName(String name) {
        log.info("Searching infos for Pokemon {}", name);
        Pokemon pokemon =
                webClient
                        .get()
                        .uri(URI_POKEMON, name)
                        .retrieve()
                        .bodyToMono(Pokemon.class).block();
        return new ResponseEntity<>(PokemonDTO.getFromEntity(pokemon), HttpStatus.OK);
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
