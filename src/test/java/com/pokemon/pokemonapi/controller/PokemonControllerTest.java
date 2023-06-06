package com.pokemon.pokemonapi.controller;

import com.pokemon.pokemonapi.service.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

    @Mock
    private PokemonService service;
    @InjectMocks
    private PokemonController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    public void shouldGetPokemonByNameWithSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/{name}", "pikachu")
        ).andDo(MockMvcResultHandlers.print());
        Mockito.verify(service, Mockito.times(1)).getByName("pikachu");
    }

    @Test
    public void shouldGetEvolutionLineByNameWithSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/{name}/evolutionLine", "pikachu")
        ).andDo(MockMvcResultHandlers.print());
        Mockito.verify(service, Mockito.times(1)).getEvolutionLineByName("pikachu");
    }
}
