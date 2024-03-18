package ru.itmo.se.exceptions;

import lombok.Getter;

public class IncorrectScriptException extends RuntimeException {

    @Getter
    private final String message;

    public IncorrectScriptException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
