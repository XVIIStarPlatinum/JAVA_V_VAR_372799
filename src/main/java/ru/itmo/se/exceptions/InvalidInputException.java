package ru.itmo.se.exceptions;

import lombok.Getter;

public class InvalidInputException extends RuntimeException {

    @Getter
    private final String message;

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
