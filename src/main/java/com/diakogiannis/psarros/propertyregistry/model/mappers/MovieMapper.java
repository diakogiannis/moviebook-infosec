package com.diakogiannis.psarros.propertyregistry.model.mappers;

import com.diakogiannis.psarros.propertyregistry.model.dto.MovieDTO;
import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Movie;
import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {

    /**
     *
     * @param movie
     * @return
     */
    public MovieDTO toMovieDTO(Movie movie) {
        if (movie == null) {
            return null;
        }
        String username = null;
        String firstname = null;
        String lastname = null;
        if(movie.getUser() != null){
            username = movie.getUser().getUsername();
            firstname = movie.getUser().getFirstname();
            lastname = movie.getUser().getLastname();
        }
        return new MovieDTO(movie.getTitle(), movie.getDescription(), username, movie.getLikes(), movie.getHates(), movie.getPublicationDate(), firstname, lastname);
    }

    /**
     *
     * @param movie
     * @return
     */
    public Iterable<MovieDTO> toMovieDTOs(Iterable<Movie> movie){
        if (movie == null){
            return null;
        }
        List movieDTOS = new ArrayList<>();
        for (Movie m : movie){
            movieDTOS.add(toMovieDTO(m));
        }
        return movieDTOS;
    }

    /**
     *
     * @param movieDTO
     * @return
     */
    public Movie toMovie(MovieDTO movieDTO){
        if(movieDTO == null){
            return null;
        }

        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        Users user = new Users();
        user.setFirstname(movieDTO.getFirstName());
        user.setLastname(movieDTO.getLastName());
        user.setUsername(movieDTO.getUsername());
        movie.setUser(user);
        movie.setLikes(movieDTO.getLikes());
        movie.setHates(movieDTO.getHates());
        movie.setPublicationDate(movieDTO.getPublicationDate());
        return movie;
    }
}
