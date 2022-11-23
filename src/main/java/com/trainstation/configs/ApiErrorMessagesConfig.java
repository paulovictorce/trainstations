package com.trainstation.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApiErrorMessagesConfig {

    private HttpStatus status;
    private List<String> errors;

    public ApiErrorMessagesConfig(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public ApiErrorMessagesConfig(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = Arrays.asList(error);
    }

}
