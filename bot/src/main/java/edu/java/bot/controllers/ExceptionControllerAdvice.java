package edu.java.bot.controllers;

import java.util.Arrays;
import java.util.Collections;
import model.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse exceptionInvalidUpdate(Exception e) {
        return ResponseEntity
            .badRequest()
            .body(new ApiErrorResponse()
                .code("400")
                .description("Invalid update")
                .exceptionName(e.getClass().getName())
                .exceptionMessage(e.getMessage())
                .stacktrace(Collections.singletonList(Arrays.asList(e.getStackTrace()).toString()))).getBody();
    }
}
