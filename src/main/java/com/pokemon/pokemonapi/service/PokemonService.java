package com.pokemon.pokemonapi.service;

import com.pokemon.pokemonapi.domain.dto.Pokemon;
import org.springframework.http.ResponseEntity;

public interface PokemonService {
    ResponseEntity<Pokemon> getByName(String name);
}
