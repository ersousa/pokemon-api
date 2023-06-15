package com.pokemon.pokemonapi.service;

import com.pokemon.pokemonapi.domain.*;
import com.pokemon.pokemonapi.domain.dto.EvolutionaryLineDTO;
import com.pokemon.pokemonapi.domain.dto.PokemonDTO;
import com.pokemon.pokemonapi.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {
    @InjectMocks
    private PokemonServiceImpl service;

    @Mock
    private WebClient webClientMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    private Pokemon defaultPokemon;

    private PokemonSpecie defaultSpecie;

    private EvolutionaryLine evolutionaryLine;
    private EvolutionaryLineDTO defaultEvolutionaryLineDTO;


    @BeforeEach
    void setup() {
        defaultPokemon = createDefaultPokemon();
        defaultSpecie = createDefaultSpecie();
        evolutionaryLine = createEvolutionaryLine();
        defaultEvolutionaryLineDTO = createDefaultEvolutionaryLineDTO();
        setWebClientMock();
    }


    private void setWebClientMock() {
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString(), anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    }

    private Pokemon createDefaultPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Teste");
        return pokemon;
    }

    private PokemonSpecie createDefaultSpecie() {
        PokemonSpecie specie = new PokemonSpecie();
        EvolutionChainUrl evolutionChainUrl = new EvolutionChainUrl();
        evolutionChainUrl.setUrl("teste");
        specie.setEvolutionChain(evolutionChainUrl);
        return specie;
    }

    private EvolutionaryLine createEvolutionaryLine() {
        EvolutionaryLine evolutionaryLine = new EvolutionaryLine();
        EvolutionChain evolutionChain = new EvolutionChain();
        evolutionChain.setEvolvesTo(new ArrayList<>());
        evolutionaryLine.setChain(evolutionChain);
        return evolutionaryLine;
    }

    private EvolutionaryLineDTO createDefaultEvolutionaryLineDTO() {
        EvolutionaryLineDTO evolutionaryLineDTO = new EvolutionaryLineDTO();
        evolutionaryLineDTO.setForms(new ArrayList<>());
        return evolutionaryLineDTO;
    }

    @Test
    void shouldGetPokemonByNameWithSuccess() {
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<Pokemon>>notNull())).thenReturn(Mono.just(defaultPokemon));
        PokemonDTO response = service.getByName("pokename").getBody();
        Assertions.assertEquals(PokemonDTO.getFromEntity(defaultPokemon), response);
    }

    @Test
    void shouldReturnNotFoundWhenIsAnInvalidPokemonName(){
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<Pokemon>>notNull())).thenThrow(WebClientResponseException.NotFound.class);
        Exception exception = Assertions.assertThrows(WebClientResponseException.NotFound.class,
                () -> service.getByName("notAValidPokemon").getBody());

        Assertions.assertEquals(WebClientResponseException.NotFound.class, exception.getClass());
    }

//    @Test
    void shouldGetEvolutionaryLineWithSuccess(){

    }

}

