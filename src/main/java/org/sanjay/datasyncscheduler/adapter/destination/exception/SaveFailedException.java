package org.sanjay.datasyncscheduler.adapter.destination.exception;

public class SaveFailedException extends Exception{

    public SaveFailedException(String message) {
        super(message);
    }

    public SaveFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
