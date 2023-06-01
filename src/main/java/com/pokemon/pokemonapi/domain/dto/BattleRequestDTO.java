package com.pokemon.pokemonapi.domain.dto;

import lombok.Data;

@Data
public class BattleRequestDTO {
    private String challenger;
    private String challenged;
}
