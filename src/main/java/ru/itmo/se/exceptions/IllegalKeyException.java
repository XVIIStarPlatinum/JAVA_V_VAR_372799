package ru.itmo.se.exceptions;

import lombok.Getter;

public class IllegalKeyException extends RuntimeException {

    @Getter
    private final String message;

    public IllegalKeyException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
