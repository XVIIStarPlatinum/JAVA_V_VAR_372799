package ru.itmo.se.exceptions;

import lombok.Getter;

public class ValueRangeException extends RuntimeException {

    @Getter
    private final String message;

    public ValueRangeException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
