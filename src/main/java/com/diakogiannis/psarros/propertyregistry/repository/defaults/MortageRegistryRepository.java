package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.MortageRegistry;
import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortageRegistryRepository extends JpaRepository<MortageRegistry, Long> {
}
