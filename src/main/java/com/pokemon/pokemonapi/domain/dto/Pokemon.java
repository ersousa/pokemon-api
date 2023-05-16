package com.pokemon.pokemonapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Pokemon {
    private String name;
    private Long height;
    private Long weight;
    @JsonAlias("location_area_encounters")
    private String locationAreaEncounters;
    private List<PokemonStats> stats;
    private List<PokemonTypes> types;
}
