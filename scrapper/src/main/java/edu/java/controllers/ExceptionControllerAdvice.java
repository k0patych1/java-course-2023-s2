package edu.java.controllers;

import edu.java.exceptions.LinkNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import model.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({LinkNotFoundException.class})
    public ApiErrorResponse linkNotFound(LinkNotFoundException e) {
        return ResponseEntity
            .badRequest()
            .body(new ApiErrorResponse()
                .code("400")
                .description("Некорректные параметры запроса")
                .exceptionName(e.getClass().getName())
                .exceptionMessage(e.getMessage())
                .stacktrace(Collections.singletonList(Arrays.asList(e.getStackTrace()).toString()))).getBody();
    }
}
