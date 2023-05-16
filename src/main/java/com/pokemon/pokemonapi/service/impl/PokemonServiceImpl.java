package com.pokemon.pokemonapi.service.impl;

import com.pokemon.pokemonapi.domain.dto.Pokemon;
import com.pokemon.pokemonapi.service.PokemonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@AllArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final WebClient webClient;
    private static final String URI_POKEMON = "/pokemon/{name}";
    @Override
    public ResponseEntity<Pokemon> getByName(String name) {
        Pokemon pokemon =
                webClient
                .get()
                .uri(URI_POKEMON, name)
                .retrieve()
                .bodyToMono(Pokemon.class).block();
        return new ResponseEntity(pokemon, HttpStatus.OK);
    }
}
