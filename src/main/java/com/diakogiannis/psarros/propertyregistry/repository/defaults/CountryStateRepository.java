package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryStateRepository extends JpaRepository<Currency, Long> {
}
