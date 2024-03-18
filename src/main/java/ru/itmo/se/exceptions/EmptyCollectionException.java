package ru.itmo.se.exceptions;

import lombok.Getter;

public class EmptyCollectionException extends RuntimeException {
    @Getter
    private final String message;

    public EmptyCollectionException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
