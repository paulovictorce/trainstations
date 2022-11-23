package com.trainstation.exceptions;

public class UniqueException extends RuntimeException{

    private String field;

    public UniqueException(String string) {
        super(string);
    }

    public UniqueException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
