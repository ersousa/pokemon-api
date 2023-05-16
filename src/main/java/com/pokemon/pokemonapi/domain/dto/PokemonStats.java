package com.pokemon.pokemonapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class PokemonStats {
    @JsonAlias("base_stat")
    private String baseStat;
    private String effort;
    private Stat stat;
}
