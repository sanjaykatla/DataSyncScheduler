package org.sanjay.datasyncscheduler.adapter.source.exception;

public class SourceServiceException extends Exception {
    public SourceServiceException(String message) {
        super(message);
    }

    public SourceServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
