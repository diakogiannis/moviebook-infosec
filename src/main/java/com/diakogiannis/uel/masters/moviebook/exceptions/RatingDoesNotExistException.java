package com.diakogiannis.uel.masters.moviebook.exceptions;

public class RatingDoesNotExistException extends RuntimeException {

    public RatingDoesNotExistException() {
        super();
    }

    public RatingDoesNotExistException(String message, String username, Long movieId) {

        super(String.format(message, username, movieId));
    }

    public RatingDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
