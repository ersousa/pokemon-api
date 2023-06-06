package com.pokemon.pokemonapi.domain.dto;

import com.pokemon.pokemonapi.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class DtoTest {
    @InjectMocks
    private Type type;
    @InjectMocks
    private Stat stat;
    @InjectMocks
    private PokemonTypes pokemonTypes;
    @InjectMocks
    private PokemonStats pokemonStats;
    @InjectMocks
    private Pokemon pokemon;
    @InjectMocks
    private LocationAreaEncounters locationAreaEncounters;
    @InjectMocks
    private ErrorDTO errorDTO;

    @Test
    public void testErrorDto(){
        errorDTO.setMessage("teste");
        errorDTO.getMessage();
        errorDTO.setStatus(33);
        errorDTO.getStatus();
        errorDTO.setTimestamp(new Date());
        errorDTO.getStatus();
    }

    @Test
    public void testLocationAreaEncounters(){
        locationAreaEncounters.setName("asdf");
        locationAreaEncounters.getName();
        locationAreaEncounters.setUrl("wwwww");
        locationAreaEncounters.getUrl();
    }

    @Test
    public void testPokemon(){
        pokemon.setName("pikachuchu");
        pokemon.getName();
        pokemon.setHeight(12L);
        pokemon.getHeight();
        pokemon.setTypes(new ArrayList<>());
        pokemon.getTypes();
        pokemon.setWeight(122L);
        pokemon.getWeight();
        pokemon.setLocationAreaEncounters("locationtese");
        pokemon.getLocationAreaEncounters();
        pokemon.setStats(new ArrayList<>());
        pokemon.getStats();
        pokemon.toString();
        pokemon.equals(new Pokemon());
        pokemon.equals("");
        pokemon.hashCode();
    }

    @Test
    public void testPokemonStats(){
        pokemonStats.setBaseStat("LeTeste");
        pokemonStats.getBaseStat();
        pokemonStats.setStat(new Stat());
        pokemonStats.getStat();
        pokemonStats.setEffort("Effforte");
        pokemonStats.getEffort();
    }

    @Test
    public void testPokemonTypes(){
        pokemonTypes.setType(new Type());
        pokemonTypes.getType();
        pokemonTypes.setSlot("kkk");
        pokemonTypes.getSlot();
    }

    @Test
    public void testStat(){
        stat.setName("statName");
        stat.getName();
        stat.setUrl("wwwwwww");
        stat.getUrl();
    }

}
