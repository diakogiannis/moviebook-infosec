package com.diakogiannis.psarros.propertyregistry.repository.defaults;

import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.AssetNotes;
import com.diakogiannis.psarros.propertyregistry.model.entity.defaults.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetNotesRepository extends JpaRepository<AssetNotes, Long> {
}
