package com.diakogiannis.psarros.propertyregistry.repository;

import com.diakogiannis.psarros.propertyregistry.model.dto.MovieRatingDTO;
import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")})
    @Query("from Rating r left join fetch r.movie  where r.user.username = :username and r.movie.movieId = :movieId")
    Optional<Rating> findRatedMovie(@Param("username") String username, @Param("movieId") Long movieId);

    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")})
    @Query("select new com.diakogiannis.psarros.propertyregistry.model.dto.MovieRatingDTO(r.user.username, r.movie.movieId, r.isLike) from Rating r where r.user.username = :username ")
    List<MovieRatingDTO> findRatedMoviesByUser(@Param("username") String username);

    @Modifying(clearAutomatically = true)
    @Query("update Rating r set r.isLike=:isLike where r.ratingId=:ratingId")
    int updateLike(@Param("isLike") Boolean isLike, @Param("ratingId") Long ratingId);


}
