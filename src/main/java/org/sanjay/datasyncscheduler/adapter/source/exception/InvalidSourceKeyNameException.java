package org.sanjay.datasyncscheduler.adapter.source.exception;

public class InvalidSourceKeyNameException extends Exception {
    public InvalidSourceKeyNameException(String message) {
        super(message);
    }

    public InvalidSourceKeyNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
