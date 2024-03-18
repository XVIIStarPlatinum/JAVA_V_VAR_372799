package ru.itmo.se.exceptions;

import lombok.Getter;

public class EmptyHistoryException extends RuntimeException {

    @Getter
    private final String message;

    public EmptyHistoryException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
