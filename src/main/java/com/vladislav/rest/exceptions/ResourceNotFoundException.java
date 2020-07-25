package com.vladislav.rest.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    @SuppressWarnings("unused")
    public ResourceNotFoundException() {
        super("Could not find resource");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
