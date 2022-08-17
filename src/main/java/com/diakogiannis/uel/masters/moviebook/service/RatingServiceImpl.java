package com.diakogiannis.uel.masters.moviebook.service;

import com.diakogiannis.uel.masters.moviebook.enums.LikeEnum;
import com.diakogiannis.uel.masters.moviebook.exceptions.MovieDoesNotExistException;
import com.diakogiannis.uel.masters.moviebook.exceptions.MovieSelfVoteException;
import com.diakogiannis.uel.masters.moviebook.exceptions.RatingDoesNotExistException;
import com.diakogiannis.uel.masters.moviebook.exceptions.VoteAlreadyExistsException;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Movie;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Rating;
import com.diakogiannis.uel.masters.moviebook.repository.MovieRepository;
import com.diakogiannis.uel.masters.moviebook.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Secured("ROLE_USER")
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserService userService;

    @Override
    @Secured("ROLE_USER")
    public void castVote(LikeEnum action, String username, Optional<Movie> movie) throws MovieDoesNotExistException, MovieSelfVoteException, RatingDoesNotExistException {


        // Movie does not exists, get out now!
        if (!movie.isPresent()) {
            throw new MovieDoesNotExistException("Username %s tried to rate a movie that does not exists", username);
        }

        Optional<Rating> rating = ratingRepository.findRatedMovie(username, movie.get().getMovieId());

        //Movie exists, lets move on to check the user
        if (username.equals(movie.get().getUser().getUsername())) {
            throw new MovieSelfVoteException("Username %s tried to self rate a movie with id %s ", username, movie.get().getMovieId());
        }

        if (LikeEnum.UNDO.equals(action)) {
            undoVote(movie.get(), rating, username);
        } else if (LikeEnum.LIKE.equals(action)) {
            likeVote(movie.get(), rating, username);
        } else if (LikeEnum.HATE.equals(action)) {
            hateVote(movie.get(), rating, username);
        }


    }

    private void undoVote(Movie movie, Optional<Rating> rating, String username) throws RatingDoesNotExistException {
        if (!rating.isPresent()) {
            throw new RatingDoesNotExistException("User tried to undo a vote that does not exists in movie with id %s ", username, movie.getMovieId());
        }
        Rating undoVote = rating.get();
        if (undoVote.getIsLike()) {
            movieRepository.removeLike(movie.getMovieId());
        } else {
            movieRepository.removeHate(movie.getMovieId());
        }

        ratingRepository.delete(undoVote);


    }

    private void likeVote(Movie movie, Optional<Rating> rating, String username) {
        //check if an opposite rating exists
        Rating vote;
        if (rating.isPresent()) {
            vote = rating.get();
            if (vote.getIsLike()) {
                throw new VoteAlreadyExistsException("Username %s tried to cast a same vote for a second time in movie with id %s ", username, movie.getMovieId());
            }
            movieRepository.reverseHate(movie.getMovieId());
            ratingRepository.updateLike(Boolean.TRUE, vote.getRatingId());
        } else {
            vote = new Rating(null, null, Boolean.TRUE, movie, userService.findUserByUsername(username));
            movieRepository.addLike(movie.getMovieId());
            ratingRepository.save(vote);
        }
    }

    private void hateVote(Movie movie, Optional<Rating> rating, String username) {
        //check if an opposite rating exists
        Rating vote;
        if (rating.isPresent()) {
            vote = rating.get();
            if (!vote.getIsLike()) {
                throw new VoteAlreadyExistsException("Username %s tried to cast a same vote for a second time in movie with id %s ", username, movie.getMovieId());
            }
            movieRepository.reverseLike(movie.getMovieId());
            ratingRepository.updateLike(Boolean.FALSE, vote.getRatingId());
        } else {
            vote = new Rating(null, null, Boolean.FALSE, movie, userService.findUserByUsername(username));
            movieRepository.addHate(movie.getMovieId());
            ratingRepository.save(vote);
        }
    }

}
