package com.pokemon.pokemonapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemon.pokemonapi.domain.dto.BattleRequestDTO;
import com.pokemon.pokemonapi.service.BattleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BattleControllerTest {

    @InjectMocks
    BattleController controller;

    @Mock
    BattleService battleService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    public void shouldStartBattleWithSuccess() throws Exception {

        BattleRequestDTO requestDTO = new BattleRequestDTO("challenger", "challenged");
        mockMvc.perform(MockMvcRequestBuilders.post("/battle")
                .content(asJsonString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print());
        Mockito.verify(battleService, Mockito.times(1)).startBattle(requestDTO);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
