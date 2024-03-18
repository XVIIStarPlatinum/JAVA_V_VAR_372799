package ru.itmo.se.exceptions;

import lombok.Getter;

public class InvalidTypeException extends RuntimeException {

    @Getter
    private final String message;

    public InvalidTypeException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
