package ru.itmo.se.exceptions;

import lombok.Getter;

public class IllegalStateException extends RuntimeException {

    @Getter
    private final String message;

    public IllegalStateException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
