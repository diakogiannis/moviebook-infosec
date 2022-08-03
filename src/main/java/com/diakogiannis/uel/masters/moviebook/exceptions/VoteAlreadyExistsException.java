package com.diakogiannis.uel.masters.moviebook.exceptions;

public class VoteAlreadyExistsException extends RuntimeException {

    public VoteAlreadyExistsException() {
        super();
    }

    public VoteAlreadyExistsException(String message, String username, Long movieId) {

        super(String.format(message, username, movieId));
    }

    public VoteAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
