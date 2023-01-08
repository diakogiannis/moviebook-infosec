package com.diakogiannis.psarros.propertyregistry.model.entity.users;

import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Movie;
import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.data.domain.Persistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(indexes = {
        @Index(name = "IDX_USERNAME", columnList = "username"),
        @Index(name = "IDX_PUBIDNF", columnList = "publicIdentifier")

})

@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users implements Persistable, Serializable {

    @Id
    @GeneratedValue
    private Long userId;
    @NaturalId
    @Column(nullable = false, unique = true)
    private String username;
    private String firstname;
    private String lastname;
    private String publicIdentifier;
    private String password;
    private Boolean enabled;
    private transient Boolean isNew;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Movie> movies = new ArrayList<>();
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Rating> ratings = new ArrayList<>();

    public Users(@NonNull String username, @NonNull String firstname, @NonNull String lastname, @NonNull String password, @NonNull Boolean enabled) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    public Object getId() {
        return this.username;
    }

    //Prevent spring data from doing a select before insert. I like to do this manually in order to prevent merging of existing data in this case
    @Override
    public boolean isNew() {
        if (this.isNew == null) {
            return Boolean.TRUE;
        } else {
            return this.isNew;
        }
    }
}
