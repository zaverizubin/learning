package org.jobrunr.example.exceptions;

public class NonRetryableException extends RuntimeException{
    private String message;

    public NonRetryableException(final String message) {
        this.message = message;
    }
}
