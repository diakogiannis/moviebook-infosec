package com.diakogiannis.psarros.propertyregistry.exceptions;

public class MovieDoesNotExistException extends RuntimeException {

    public MovieDoesNotExistException() {
        super();
    }

    public MovieDoesNotExistException(String message, String username) {

        super(String.format(message, username));
    }

    public MovieDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
