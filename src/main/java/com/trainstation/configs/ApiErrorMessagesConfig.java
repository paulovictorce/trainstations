package com.trainstation.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ApiErrorMessagesConfig {

    private HttpStatus status;
    private Set<String> errors;

    public ApiErrorMessagesConfig(HttpStatus status, Set<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public ApiErrorMessagesConfig(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = Set.of(error);
    }

}
