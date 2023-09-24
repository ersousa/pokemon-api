package com.pokemon.pokemonapi.service.impl;

import com.pokemon.pokemonapi.domain.PokemonStats;
import com.pokemon.pokemonapi.domain.dto.BattleRequestDTO;
import com.pokemon.pokemonapi.domain.dto.BattleResponseDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import com.pokemon.pokemonapi.service.BattleService;
import com.pokemon.pokemonapi.service.PokemonService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final WebClient webClient;

    private final PokemonService pokemonService;
    @Override
    public ResponseEntity<BattleResponseDTO> startBattle(final BattleRequestDTO requestDTO) {

        ResponseEntity<PokemonDTO> challenger = pokemonService.getByName(requestDTO.getChallenger());
        ResponseEntity<PokemonDTO> challenged = pokemonService.getByName(requestDTO.getChallenged());

        if(HttpStatus.SERVICE_UNAVAILABLE.equals(challenger.getStatusCode())
            || HttpStatus.SERVICE_UNAVAILABLE.equals(challenged.getStatusCode())){
            return new ResponseEntity<>(new BattleResponseDTO("Battle unavailable :("), HttpStatus.SERVICE_UNAVAILABLE);
        }

        log.info("Starting battle between {} and {}", requestDTO.getChallenger(), requestDTO.getChallenged());
        Integer result = getTotalStats(challenger).compareTo(getTotalStats(challenged));

        String winner = "DRAW";
        if(result > 0){
            winner = requestDTO.getChallenger();
        } else if(result < 0){
            winner = requestDTO.getChallenged();
        }

        log.info("Battle succeed!! the winner is: " + winner);
        return new ResponseEntity<>(new BattleResponseDTO(winner), HttpStatus.OK);
    }

    private static Integer getTotalStats(ResponseEntity<PokemonDTO> battler) {
        Integer totalStat = 0;
        if (Objects.nonNull(battler.getBody()) && Objects.nonNull(battler.getBody().getStats())) {
            totalStat = battler.getBody().getStats().stream()
                    .map(PokemonStats::getBaseStat)
                    .mapToInt(Integer::parseInt)
                    .sum();
        }
        return totalStat;
    }
}
