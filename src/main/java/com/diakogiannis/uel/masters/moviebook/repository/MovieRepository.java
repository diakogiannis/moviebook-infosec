package com.diakogiannis.uel.masters.moviebook.repository;


import com.diakogiannis.uel.masters.moviebook.model.entity.movies.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {


    @Query("from Movie m left join fetch m.user left join fetch m.rating where m.movieId=:movieId")
    Optional<Movie> findMovie(@Param("movieId") Long movieId);

    @Query("from Movie m left join fetch m.user where m.movieId=:movieId")
    Optional<Movie> findMovieWithUserDetails(@Param("movieId") Long movieId);

    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")})
    @Query("from Movie m left join fetch m.user")
    Iterable<Movie> findAllMovies(Sort sort);

    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")})
    @Query("from Movie m left join fetch m.user where m.user.publicIdentifier=:publicIdentifier")
    Iterable<Movie> findAllMoviesWithUserFilter(Sort sort, @Param("publicIdentifier") String publicIdentifier);

    @Secured("ROLE_USER")
    @Modifying
    @Query("update Movie set likes=likes+1 where movieId=:movieId")
    void addLike(Long movieId);

    @Secured("ROLE_USER")
    @Modifying
    @Query("update Movie set hates=hates+1 where movieId=:movieId")
    void addHate(Long movieId);

    @Secured("ROLE_USER")
    @Modifying
    @Query("update Movie set likes=likes-1 where movieId=:movieId")
    void removeLike(Long movieId);

    @Secured("ROLE_USER")
    @Modifying
    @Query("update Movie set hates=hates-1 where movieId=:movieId")
    void removeHate(Long movieId);

    @Secured("ROLE_USER")
    @Modifying
    @Query("update Movie set hates=hates-1, likes=likes+1 where movieId=:movieId")
    void reverseHate(Long movieId);

    @Secured("ROLE_USER")
    @Modifying
    @Query("update Movie set hates=hates+1, likes=likes-1 where movieId=:movieId")
    void reverseLike(Long movieId);


}
