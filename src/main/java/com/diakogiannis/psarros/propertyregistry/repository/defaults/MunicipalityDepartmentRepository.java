package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.MunicipalityDepartment;
import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityDepartmentRepository extends JpaRepository<MunicipalityDepartment, Long> {
}
