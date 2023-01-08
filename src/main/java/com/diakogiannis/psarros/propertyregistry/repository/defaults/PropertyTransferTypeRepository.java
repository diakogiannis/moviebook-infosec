package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.PropertyTransferType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyTransferTypeRepository extends JpaRepository<PropertyTransferType, Long> {
}
