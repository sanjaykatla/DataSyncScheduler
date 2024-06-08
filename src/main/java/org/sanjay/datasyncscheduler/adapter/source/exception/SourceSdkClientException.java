package org.sanjay.datasyncscheduler.adapter.source.exception;

public class SourceSdkClientException extends Exception {
    public SourceSdkClientException(String message) {
        super(message);
    }

    public SourceSdkClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
