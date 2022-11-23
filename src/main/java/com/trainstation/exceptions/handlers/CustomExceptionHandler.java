package com.trainstation.exceptions.handlers;

import com.trainstation.configs.ApiErrorMessagesConfig;
import com.trainstation.exceptions.UniqueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorMessagesConfig apiErrorMessagesConfig = new ApiErrorMessagesConfig(status, errors);

        return new ResponseEntity<>(apiErrorMessagesConfig, apiErrorMessagesConfig.getStatus());
    }

    @ExceptionHandler(UniqueException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UniqueException exception, WebRequest request) {

        ApiErrorMessagesConfig apiErrorMessage = new ApiErrorMessagesConfig(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());
    }

}
