package com.pokemon.pokemonapi.service;

import com.pokemon.pokemonapi.domain.dto.BattleRequestDTO;
import com.pokemon.pokemonapi.domain.dto.BattleResponseDTO;
import org.springframework.http.ResponseEntity;

public interface BattleService {
    ResponseEntity<BattleResponseDTO> startBattle(final BattleRequestDTO requestDTO);
}
