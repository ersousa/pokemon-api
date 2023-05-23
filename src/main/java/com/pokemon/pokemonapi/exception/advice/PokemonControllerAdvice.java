package com.pokemon.pokemonapi.exception.advice;

import com.pokemon.pokemonapi.domain.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;

@ControllerAdvice
@RestController
public class PokemonControllerAdvice {

    @ExceptionHandler(value = WebClientResponseException.NotFound.class)
    public ErrorDTO notFoundException(final WebClientResponseException.NotFound notFoundException){
        return new ErrorDTO()
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Pokemon not found.")
                .timestamp(new Date())
                .build();
    }
}
