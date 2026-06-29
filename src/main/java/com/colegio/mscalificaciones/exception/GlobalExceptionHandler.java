package com.colegio.mscalificaciones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import feign.FeignException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, String>> manejarFeignNotFound(FeignException.NotFound ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Validacion fallida: El recurso solicitado no existe en el microservicio externo.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}