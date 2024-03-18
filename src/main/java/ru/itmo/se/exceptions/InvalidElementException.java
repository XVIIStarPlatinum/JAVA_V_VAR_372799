package ru.itmo.se.exceptions;

import lombok.Getter;

public class InvalidElementException extends RuntimeException {

    @Getter
    private final String message;

    public InvalidElementException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
