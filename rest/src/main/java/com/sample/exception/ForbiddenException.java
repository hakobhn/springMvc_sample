package com.sample.exception;

/**
 * Created by hakob on 4/3/18.
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {}
}
