package com.diakogiannis.uel.masters.moviebook.model.entity.movies;

import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "IDX_MOVID", columnList = "movieId"),
        @Index(name = "IDX_PUBDATE", columnList = "publicationDate"),
        @Index(name = "IDX_LIKES", columnList = "likes"),
        @Index(name = "IDX_HATES", columnList = "hates")
})
@Data
@NoArgsConstructor
public class Movie implements Serializable {

    @Id
    @GeneratedValue
    private Long movieId;
    private @NonNull String title;
    @Lob
    private @NonNull String description;
    @CreationTimestamp
    private LocalDateTime publicationDate;
    private Long likes = 0l;
    private Long hates = 0l;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private Users user;

    @OneToMany
    @JoinColumn(name = "fk_movie")
    private List<Rating> rating = new ArrayList<>();

}
