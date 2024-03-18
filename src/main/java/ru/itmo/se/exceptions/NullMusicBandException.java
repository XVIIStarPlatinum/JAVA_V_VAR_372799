package ru.itmo.se.exceptions;

import lombok.Getter;

public class NullMusicBandException extends RuntimeException {

    @Getter
    private final String message;

    public NullMusicBandException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
