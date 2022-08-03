package com.diakogiannis.uel.masters.moviebook.exceptions;

public class MovieSelfVoteException extends RuntimeException {

    public MovieSelfVoteException() {
        super();
    }

    public MovieSelfVoteException(String message, String username, Long movieId) {

        super(String.format(message, username, movieId));
    }

    public MovieSelfVoteException(String message, Throwable cause) {
        super(message, cause);
    }

}
