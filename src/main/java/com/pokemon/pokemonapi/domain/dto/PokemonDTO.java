package com.pokemon.pokemonapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.pokemon.pokemonapi.domain.Pokemon;
import com.pokemon.pokemonapi.domain.PokemonStats;
import com.pokemon.pokemonapi.domain.PokemonTypes;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class PokemonDTO {
    private String name;
    private Long height;
    private Long weight;
    @JsonAlias("location_area_encounters")
    private String locationAreaEncounters;
    private List<PokemonStats> stats;
    private List<PokemonTypes> types;

    public static PokemonDTO getFromEntity(Pokemon pokemon) {
        return PokemonDTO.builder()
                .name(pokemon.getName())
                .height(pokemon.getHeight())
                .weight(pokemon.getWeight())
                .locationAreaEncounters(pokemon.getLocationAreaEncounters())
                .stats(pokemon.getStats())
                .types(pokemon.getTypes())
                .build();
    }
}
