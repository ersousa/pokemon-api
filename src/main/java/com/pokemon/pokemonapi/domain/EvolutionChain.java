package com.pokemon.pokemonapi.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class EvolutionChain {
    @JsonAlias("evolves_to")
    private List<Evolution> evolvesTo;
}
