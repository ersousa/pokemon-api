package com.pokemon.pokemonapi.service;

import com.pokemon.pokemonapi.domain.dto.Pokemon;
import com.pokemon.pokemonapi.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceImplTest {
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


    @BeforeEach
    void setup() {
        defaultPokemon = createDefaultPokemon();
    }

    private Pokemon createDefaultPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Teste");
        return pokemon;
    }

    @Test
    public void shouldGetPokemonByNameWithSuccess() {
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString(), anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<Pokemon>>notNull())).thenReturn(Mono.just(defaultPokemon));

        Pokemon response = service.getByName("pokename").getBody();
        Assertions.assertEquals(defaultPokemon, response);
    }
}