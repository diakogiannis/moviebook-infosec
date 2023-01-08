package com.diakogiannis.psarros.propertyregistry.service;

import com.diakogiannis.psarros.propertyregistry.exceptions.MovieDoesNotExistException;
import com.diakogiannis.psarros.propertyregistry.exceptions.RatingDoesNotExistException;
import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Movie;
import com.diakogiannis.psarros.propertyregistry.enums.LikeEnum;
import com.diakogiannis.psarros.propertyregistry.exceptions.MovieSelfVoteException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RatingService {
    @Secured("ROLE_USER")
    @Transactional
    void castVote(LikeEnum action, String username, Optional<Movie> movie) throws MovieDoesNotExistException, MovieSelfVoteException, RatingDoesNotExistException;


}
