package com.pokemon.pokemonapi.controller;

import com.pokemon.pokemonapi.domain.dto.BattleRequestDTO;
import com.pokemon.pokemonapi.domain.dto.BattleResponseDTO;
import com.pokemon.pokemonapi.service.BattleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/battle")
@RequiredArgsConstructor
@Slf4j
public class BattleController {

    private final BattleService battleService;

    @PostMapping
    @ApiOperation(value = "Starts a battle between 2 pokemons.")
    public ResponseEntity<BattleResponseDTO> startBattle(@RequestBody final BattleRequestDTO requestDTO){
        return battleService.startBattle(requestDTO);
    }
}
