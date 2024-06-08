package org.sanjay.datasyncscheduler.adapter.source.exception;

public class SourceException extends Exception {
    public SourceException(String message) {
        super(message);
    }

    public SourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
