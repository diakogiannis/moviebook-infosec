package com.diakogiannis.psarros.propertyregistry.model.entity.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(indexes = {
        @Index(name = "IDX_AUTH_USERNAME", columnList = "username")
})
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Authorities implements Persistable, Serializable {

    @Id
    private @NonNull String username;
    private @NonNull String authority;
    private transient Boolean isNew;

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
