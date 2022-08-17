package com.diakogiannis.uel.masters.moviebook.model.entity.movies;

import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "IDX_RATID", columnList = "ratingId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private Long ratingId;
    @CreationTimestamp
    private LocalDateTime ratingDate;
    //0 is hate and 1 is like
    private @NonNull Boolean isLike;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_movie")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

}
