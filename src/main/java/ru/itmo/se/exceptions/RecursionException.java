package ru.itmo.se.exceptions;

import lombok.Getter;

public class RecursionException extends RuntimeException {

    @Getter
    private final String message;

    public RecursionException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
