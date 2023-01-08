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
        @Index(name = "IDX_PropertyType_ID", columnList = "delosId")
})
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PropertyType {

    /*@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID PropertyTypeId;*/

    @Id
    private Long delosId;
    private String name;

}
