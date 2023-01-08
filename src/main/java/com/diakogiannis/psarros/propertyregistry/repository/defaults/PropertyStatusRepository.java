package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyStatusRepository extends JpaRepository<PropertyStatus, Long> {
}
