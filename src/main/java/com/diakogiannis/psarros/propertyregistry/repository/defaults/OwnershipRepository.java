package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.Ownership;
import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.OwnershipType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
}
