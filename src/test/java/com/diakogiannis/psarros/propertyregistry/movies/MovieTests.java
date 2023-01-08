package com.diakogiannis.psarros.propertyregistry.movies;

import com.diakogiannis.psarros.propertyregistry.enums.LikeEnum;
import com.diakogiannis.psarros.propertyregistry.enums.SortByEnum;
import com.diakogiannis.psarros.propertyregistry.exceptions.MovieDoesNotExistException;
import com.diakogiannis.psarros.propertyregistry.exceptions.UserExistsException;
import com.diakogiannis.psarros.propertyregistry.model.dto.MovieRatingDTO;
import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Movie;
import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import com.diakogiannis.psarros.propertyregistry.repository.MovieRepository;
import com.diakogiannis.psarros.propertyregistry.repository.RatingRepository;
import com.diakogiannis.psarros.propertyregistry.service.MovieService;
import com.diakogiannis.psarros.propertyregistry.service.RatingService;
import com.diakogiannis.psarros.propertyregistry.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieTests {

    private static Users user1;
    private static Users user2;
    private static Movie movie1;
    @Autowired
    MovieService movieService;
    @Autowired
    UserService userService;
    @Autowired
    RatingService ratingService;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RatingRepository ratingRepository;

    @Before
    public void init() throws UserExistsException {

        user1 = new Users(UUID.randomUUID().toString(), "bar", "foo", "bar", true);
        userService.registerUser(user1);
        user2 = new Users(UUID.randomUUID().toString(), "bar", "foo", "bar", true);
        userService.registerUser(user2);

        movie1 = new Movie();
        movie1.setUser(user1);
        movie1.setTitle("FOO Title");
        movie1.setDescription("FOO Description");
        movie1.setPublicationDate(LocalDateTime.now());
    }

    @Test
    public void getMovies() {
        Assert.assertTrue(StreamSupport.stream(movieService.getMovies(SortByEnum.UNKNOWN, null).spliterator(), false).count() > 0);
    }

    @WithMockUser("foo")
    @Test
    public void getMoviesWithRatings() {
        Map<Long, MovieRatingDTO> dtoMap = movieService.getUserMovieRatings("alexius");
        Assert.assertTrue(dtoMap.size() > 0);
        dtoMap.forEach((k, l) -> {
            System.out.println(k.toString() + " - " + l.toString());
        });
    }

    @WithMockUser("foo")
    @Test
    public void saveMovie() {

        Movie savedMovie = movieService.saveNewMovie(movie1, user1.getUsername());
        Assert.assertNotNull(savedMovie);
        Assert.assertNotNull(movieRepository.findMovie(savedMovie.getMovieId()));

    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void saveMovieUnAuthorised() {
        Movie savedMovie = movieService.saveNewMovie(movie1, user1.getUsername());
        Assert.assertNotNull(savedMovie);
        Assert.assertNotNull(movieRepository.findMovie(savedMovie.getMovieId()));

    }

    @Test(expected = MovieDoesNotExistException.class)
    @WithMockUser("foo")
    public void rateMovie_does_not_exists() {

        ratingService.castVote(LikeEnum.LIKE, user1.getUsername(), Optional.ofNullable(null));
    }

    @Test
    @WithMockUser("foo")
    public void rateMovie() {
        Movie savedMovie = movieService.saveNewMovie(movie1, user1.getUsername());
        ratingService.castVote(LikeEnum.LIKE, user2.getUsername(), Optional.of(savedMovie));
        Assert.assertTrue(ratingRepository.findRatedMovie(user2.getUsername(), savedMovie.getMovieId()).isPresent());
        ratingService.castVote(LikeEnum.HATE, user2.getUsername(), Optional.of(savedMovie));
        Assert.assertTrue(ratingRepository.findRatedMovie(user2.getUsername(), savedMovie.getMovieId()).isPresent());
        ratingService.castVote(LikeEnum.UNDO, user2.getUsername(), Optional.of(savedMovie));
        Assert.assertFalse(ratingRepository.findRatedMovie(user2.getUsername(), savedMovie.getMovieId()).isPresent());
        ratingService.castVote(LikeEnum.LIKE, user2.getUsername(), Optional.of(savedMovie));
        Assert.assertTrue(ratingRepository.findRatedMovie(user2.getUsername(), savedMovie.getMovieId()).isPresent());
        ratingService.castVote(LikeEnum.UNDO, user2.getUsername(), Optional.of(savedMovie));
        Assert.assertFalse(ratingRepository.findRatedMovie(user2.getUsername(), savedMovie.getMovieId()).isPresent());
    }

}
