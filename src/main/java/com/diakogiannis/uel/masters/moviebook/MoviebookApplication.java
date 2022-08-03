package com.diakogiannis.uel.masters.moviebook;

import com.diakogiannis.uel.masters.moviebook.exceptions.UserExistsException;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Movie;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Rating;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import com.diakogiannis.uel.masters.moviebook.repository.MovieRepository;
import com.diakogiannis.uel.masters.moviebook.repository.RatingRepository;
import com.diakogiannis.uel.masters.moviebook.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Application Configuration
 */
@SpringBootApplication
public class MoviebookApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MoviebookApplication.class);
    @Autowired
    DataSource dataSource;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(MoviebookApplication.class, args);
    }

    /**
     * Application Runner
     *
     * @param movieRepository
     * @param ratingRepository
     * @return
     */
    @Bean
    ApplicationRunner init(MovieRepository movieRepository, RatingRepository ratingRepository) {
        final ApplicationRunner applicationRunner = args -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            InputStream movieInputStream = TypeReference.class.getResourceAsStream("/json/movies.json");
            InputStream ratingInputStream = TypeReference.class.getResourceAsStream("/json/rating.json");
            InputStream usersInputStream = TypeReference.class.getResourceAsStream("/json/users.json");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            mapper.setDateFormat(df);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            TypeReference<List<Movie>> movieTypeReference = new TypeReference<List<Movie>>() {
            };
            TypeReference<List<Rating>> ratingTypeReference = new TypeReference<List<Rating>>() {
            };
            TypeReference<List<Users>> usersTypeReference = new TypeReference<List<Users>>() {
            };

            //import users
            List<Users> users = mapper.readValue(usersInputStream, usersTypeReference);
            users.forEach(user -> {
                try {
                    userService.registerUser(user);
                } catch (UserExistsException e) {
                    LOG.error("Unable to save users, server said: {}", e.getMessage(), e);
                }
            });

            //Import Sample Movies
            if (movieRepository.count() == 0) {

                try {
                    List<Movie> movies = mapper.readValue(movieInputStream, movieTypeReference);
                    movieRepository.saveAll(movies);
                    LOG.info("Movies Imported");
                    //Import Ratings
                    List<Rating> ratings = mapper.readValue(ratingInputStream, ratingTypeReference);
                    ratingRepository.saveAll(ratings);
                } catch (IOException e) {
                    LOG.error("Unable to save movies/ratings, server said: {}", e.getMessage(), e);
                }
            }


        };
        return applicationRunner;
    }


}
