package com.pokemon.pokemonapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleRequestDTO {
    private String challenger;
    private String challenged;
}
