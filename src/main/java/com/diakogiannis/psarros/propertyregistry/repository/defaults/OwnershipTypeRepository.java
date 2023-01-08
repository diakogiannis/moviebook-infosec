package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.OwnershipType;
import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.PropertyFloor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnershipTypeRepository extends JpaRepository<OwnershipType, Long> {
}
