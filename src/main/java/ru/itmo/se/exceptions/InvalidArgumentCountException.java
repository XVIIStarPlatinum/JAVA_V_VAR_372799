package ru.itmo.se.exceptions;

import lombok.Getter;

public class InvalidArgumentCountException extends IllegalArgumentException {

    @Getter
    private final String message;

    public InvalidArgumentCountException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
