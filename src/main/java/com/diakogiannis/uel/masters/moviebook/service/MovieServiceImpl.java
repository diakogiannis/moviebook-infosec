package com.diakogiannis.uel.masters.moviebook.service;

import com.diakogiannis.uel.masters.moviebook.enums.SortByEnum;
import com.diakogiannis.uel.masters.moviebook.repository.MovieRepository;
import com.diakogiannis.uel.masters.moviebook.repository.RatingRepository;
import com.diakogiannis.uel.masters.moviebook.model.dto.MovieRatingDTO;
import com.diakogiannis.uel.masters.moviebook.model.dto.UserDetailsDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Movie;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import com.diakogiannis.uel.masters.moviebook.model.mappers.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final UsersMapper usersMapper;
    @Resource
    MovieRepository movieRepository;
    @Resource
    RatingRepository ratingRepository;
    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;


    /**
     * @param sortBy           Integer mapped by an ENUM
     * @param publicIdentifier user
     * @return all movies with/out user
     */
    @Override
    public Iterable<Movie> getMovies(SortByEnum sortBy, String publicIdentifier) {
        if (publicIdentifier == null) {
            return getAllMoviesNoFilterNoRatings(sortBy);
        } else {
            return getAllMoviesWithUserFilterNoRatings(sortBy, publicIdentifier);
        }
    }


    private Iterable<Movie> getAllMoviesWithUserFilterNoRatings(SortByEnum sortBy, String publicIdentifier) {
        if (sortBy == null) {
            return movieRepository.findAllMoviesWithUserFilter(Sort.unsorted(), publicIdentifier);
        }
        switch (sortBy) {
            case LIKES:
                return movieRepository.findAllMoviesWithUserFilter(Sort.by("likes").descending(), publicIdentifier);
            case HATES:
                return movieRepository.findAllMoviesWithUserFilter(Sort.by("hates").descending(), publicIdentifier);
            case DATE:
                return movieRepository.findAllMoviesWithUserFilter(Sort.by("publicationDate").descending(), publicIdentifier);
            default:
                return movieRepository.findAllMoviesWithUserFilter(Sort.unsorted(), publicIdentifier);
        }

    }


    private Iterable<Movie> getAllMoviesNoFilterNoRatings(SortByEnum sortBy) {
        if (sortBy == null) {
            return movieRepository.findAllMovies(Sort.unsorted());
        }
        switch (sortBy) {
            case LIKES:
                return movieRepository.findAllMovies(Sort.by("likes").descending());
            case HATES:
                return movieRepository.findAllMovies(Sort.by("hates").descending());
            case DATE:
                return movieRepository.findAllMovies(Sort.by("publicationDate").descending());
            default:
                return movieRepository.findAllMovies(Sort.unsorted());
        }

    }


    /**
     * Saves a movie to DB
     *
     * @param movie
     * @param username
     * @return
     */
    @Override
    @Transactional
    @Secured("ROLE_USER")
    public Movie saveNewMovie(Movie movie, String username) {
        //We fetch fresh user data from DB
        UserDetailsDTO userDetailsDTO = userService.getUserDetails(username);
        Users user = new Users();
        user.setUserId(userDetailsDTO.getUserId());
        movie.setUser(user);
        movie.setLikes(0l);
        movie.setHates(0l);
        return movieRepository.save(movie);
    }


    @Override
    @Secured("ROLE_USER")
    public Map<Long, MovieRatingDTO> getUserMovieRatings(String username) {
        List<MovieRatingDTO> movieRatingDTOS = ratingRepository.findRatedMoviesByUser(username);
        Map<Long, MovieRatingDTO> movieRatingMap = new HashMap<>();

        movieRatingDTOS.forEach(m -> {
            movieRatingMap.put(m.getMovieId(), m);
        });

        return movieRatingMap;
    }

    @Override
    public Optional<Movie> findMovieWithUserDetails(Long movieId) {
        return movieRepository.findMovieWithUserDetails(movieId);

    }


}
