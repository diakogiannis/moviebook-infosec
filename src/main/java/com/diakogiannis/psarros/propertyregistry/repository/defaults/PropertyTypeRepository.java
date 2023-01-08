package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Long> {
}
