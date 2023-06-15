package com.pokemon.pokemonapi.service;

import com.pokemon.pokemonapi.domain.Pokemon;
import com.pokemon.pokemonapi.domain.PokemonStats;
import com.pokemon.pokemonapi.domain.dto.BattleRequestDTO;
import com.pokemon.pokemonapi.domain.dto.BattleResponseDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import com.pokemon.pokemonapi.service.impl.BattleServiceImpl;
import com.pokemon.pokemonapi.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BattleServiceImplTest {

    @InjectMocks
    private BattleServiceImpl battleService;

    @Mock
    private PokemonServiceImpl pokemonService;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;
    private PokemonDTO defaultChallenger;
    private PokemonDTO defaultChallenged;

    @Mock
    private WebClient webClientMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;


    @BeforeEach
    void setup() {
        defaultChallenger = getChallenger();
        defaultChallenged = getChallenged();
    }


    @Test
    public void shouldChallengerWin() {
        BattleRequestDTO battleRequestDTO = new BattleRequestDTO("pokeChallenger", "pokeChallenged");
        when(pokemonService.getByName("pokeChallenger")).thenReturn(new ResponseEntity<>(defaultChallenger, HttpStatus.OK));
        when(pokemonService.getByName("pokeChallenged")).thenReturn(new ResponseEntity<>(defaultChallenged, HttpStatus.OK));
        defaultChallenger.getStats().add(PokemonStats.builder().baseStat("10").build());
        ResponseEntity<BattleResponseDTO> battleResponseDTOResponseEntity = battleService.startBattle(battleRequestDTO);
        assertEquals(battleResponseDTOResponseEntity.getBody().getWinner(), "pokeChallenger");

    }

    @Test
    public void shouldChallengedWin() {
        BattleRequestDTO battleRequestDTO = new BattleRequestDTO("pokeChallenger", "pokeChallenged");
        when(pokemonService.getByName("pokeChallenger")).thenReturn(new ResponseEntity<>(defaultChallenger, HttpStatus.OK));
        when(pokemonService.getByName("pokeChallenged")).thenReturn(new ResponseEntity<>(defaultChallenged, HttpStatus.OK));
        defaultChallenged.getStats().add(PokemonStats.builder().baseStat("10").build());
        ResponseEntity<BattleResponseDTO> battleResponseDTOResponseEntity = battleService.startBattle(battleRequestDTO);
        assertEquals(battleResponseDTOResponseEntity.getBody().getWinner(), "pokeChallenged");

    }

 /*   @Test
    public void shouldReturnNotFoundWhenIsAnInvalidPokemonName() {
        BattleRequestDTO battleRequestDTO = new BattleRequestDTO("invalidPokeChallenger", "pokeChallenged");

        when(pokemonService.getByName("pokeChallenger")).thenReturn(new ResponseEntity<>(defaultChallenger, HttpStatus.OK));

        Exception exception = Assertions.assertThrows(WebClientResponseException.NotFound.class,
                () -> pokemonService.getByName("invalidPokeChallenger").getBody());

        battleService.startBattle(battleRequestDTO);
        Assertions.assertEquals(WebClientResponseException.NotFound.class, exception.getClass());
    }*/

    @Test
    public void shouldReturnNotFoundWhenIsAnInvalidPokemonName(){
        BattleRequestDTO battleRequestDTO = new BattleRequestDTO("invalidPokeChallenger", "pokeChallenged");
        when(pokemonService.getByName("invalidPokeChallenger")).thenThrow(WebClientResponseException.NotFound.class);
        Exception exception = Assertions.assertThrows(WebClientResponseException.NotFound.class,
                () -> battleService.startBattle(battleRequestDTO));

        Assertions.assertEquals(WebClientResponseException.NotFound.class, exception.getClass());
    }

    private PokemonDTO getChallenger() {
        List<PokemonStats> stats = new ArrayList<>();
        stats.add(PokemonStats.builder().baseStat("10").build());
        return PokemonDTO.builder().stats(stats).build();
    }

    private PokemonDTO getChallenged() {
        List<PokemonStats> stats = new ArrayList<>();
        stats.add(PokemonStats.builder().baseStat("10").build());
        return PokemonDTO.builder().stats(stats).build();
    }
}
