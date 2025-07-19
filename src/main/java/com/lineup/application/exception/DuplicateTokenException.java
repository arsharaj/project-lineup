package com.lineup.application.exception;

public class DuplicateTokenException extends Exception {

    public DuplicateTokenException(String message) {
        super(message);
    }

    public DuplicateTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
