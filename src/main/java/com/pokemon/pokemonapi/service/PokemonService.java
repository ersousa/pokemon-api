package com.pokemon.pokemonapi.service;

import com.pokemon.pokemonapi.domain.dto.EvolutionaryLineDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PokemonService {
    ResponseEntity<PokemonDTO> getByName(String name);
    ResponseEntity<EvolutionaryLineDTO> getEvolutionLineByName(String name);
}
