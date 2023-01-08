package com.diakogiannis.psarros.propertyregistry.model.entity.defaults;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {
        @Index(name = "IDX_Municipality_ID", columnList = "delosId")
})
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Municipality {
    @Id
    private Long delosId;
    private String name;
}
