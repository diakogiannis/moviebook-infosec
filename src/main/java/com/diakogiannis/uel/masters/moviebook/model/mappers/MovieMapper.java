package com.diakogiannis.uel.masters.moviebook.model.mappers;

import com.diakogiannis.uel.masters.moviebook.model.dto.MovieDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toMovieDTO(Movie movie);

    Iterable<MovieDTO> toMovieDTOs(Iterable<Movie> movie);

    Movie toMovie(MovieDTO movieDTO);
}
