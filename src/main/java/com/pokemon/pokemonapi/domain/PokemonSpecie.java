package com.pokemon.pokemonapi.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.pokemon.pokemonapi.domain.EvolutionChainUrl;
import lombok.Data;

@Data
public class PokemonSpecie {
    @JsonAlias("evolution_chain")
    private EvolutionChainUrl evolutionChain;
}
