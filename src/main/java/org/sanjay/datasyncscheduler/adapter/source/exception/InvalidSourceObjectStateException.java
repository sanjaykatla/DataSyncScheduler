package org.sanjay.datasyncscheduler.adapter.source.exception;

public class InvalidSourceObjectStateException extends Exception {
    public InvalidSourceObjectStateException(String message) {
        super(message);
    }

    public InvalidSourceObjectStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
