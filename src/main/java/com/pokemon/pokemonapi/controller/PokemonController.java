package com.pokemon.pokemonapi.controller;

import com.pokemon.pokemonapi.domain.dto.EvolutionaryLineDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import com.pokemon.pokemonapi.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pokemons")
@RequiredArgsConstructor
@Slf4j
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/{name}")
    public ResponseEntity<PokemonDTO> getByName(@PathVariable("name") final String name ){
        return pokemonService.getByName(name);
    }

    @GetMapping("/{name}/evolutionLine")
    public ResponseEntity<EvolutionaryLineDTO> getEvolutionLineByName(@PathVariable("name") final String name ){
        return pokemonService.getEvolutionLineByName(name);
    }

}
