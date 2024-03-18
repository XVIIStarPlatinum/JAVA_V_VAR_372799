package ru.itmo.se.exceptions;

import lombok.Getter;

public class NullValueException extends RuntimeException {

    @Getter
    private final String message;

    public NullValueException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
