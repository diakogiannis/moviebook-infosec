package com.diakogiannis.psarros.propertyregistry.service.defaults;

import com.diakogiannis.psarros.propertyregistry.model.enums.DefaultSetEnum;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.AssetNotesRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.CountryStateRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.CurrencyRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.MortageRegistryRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.MunicipalityDepartmentRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.MunicipalityRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.OwnershipRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.OwnershipTypeRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.PropertyFloorRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.PropertyStatusRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.PropertyTransferTypeRepository;
import com.diakogiannis.psarros.propertyregistry.repository.defaults.PropertyTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ApplicationDefaultsService {

    final AssetNotesRepository assetNotesRepository;
    final CountryStateRepository countryStateRepository;
    final CurrencyRepository currencyRepository;
    final MortageRegistryRepository mortageRegistryRepository;
    final MunicipalityDepartmentRepository municipalityDepartmentRepository;
    final MunicipalityRepository municipalityRepository;
    final OwnershipRepository ownershipRepository;
    final OwnershipTypeRepository ownershipTypeRepository;
    final PropertyFloorRepository propertyFloorRepository;
    final PropertyStatusRepository propertyStatusRepository;
    final PropertyTransferTypeRepository propertyTransferTypeRepository;
    final PropertyTypeRepository propertyTypeRepository;

    public ApplicationDefaultsService(CurrencyRepository currencyRepository, AssetNotesRepository assetNotesRepository, CountryStateRepository countryStateRepository, MortageRegistryRepository mortageRegistryRepository, MunicipalityDepartmentRepository municipalityDepartmentRepository, MunicipalityRepository municipalityRepository, OwnershipRepository ownershipRepository, OwnershipTypeRepository ownershipTypeRepository, PropertyTypeRepository propertyTypeRepository, PropertyFloorRepository propertyFloorRepository, PropertyStatusRepository propertyStatusRepository, PropertyTransferTypeRepository propertyTransferTypeRepository) {
        this.currencyRepository = currencyRepository;
        this.assetNotesRepository = assetNotesRepository;
        this.countryStateRepository = countryStateRepository;
        this.mortageRegistryRepository = mortageRegistryRepository;
        this.municipalityDepartmentRepository = municipalityDepartmentRepository;
        this.municipalityRepository = municipalityRepository;
        this.ownershipRepository = ownershipRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.propertyFloorRepository = propertyFloorRepository;
        this.propertyStatusRepository = propertyStatusRepository;
        this.propertyTransferTypeRepository = propertyTransferTypeRepository;
    }

    @Cacheable("defaultSetValues")
    public Map<String, List<?>> getDefaultSetValues(){
        Map<String, List<?>> defaultSetValues = new HashMap<>();

        defaultSetValues.put(DefaultSetEnum.ASSET_NOTES.name(),assetNotesRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.COUNTRY_STATE.name(),countryStateRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.CURRENCY.name(),currencyRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.MORTAGE_REGISTRY.name(),mortageRegistryRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.MUNICIPALITY.name(),municipalityRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.MUNICIPALITY_DEPARTMENT.name(),municipalityDepartmentRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.OWNERSHIP.name(),ownershipRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.OWNERSHIP_TYPE.name(),ownershipTypeRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.PROPERTY_FLOOR.name(),propertyFloorRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.PROPERTY_STATUS.name(),propertyStatusRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.PROPERTY_TRANSFER_TYPE.name(),propertyTransferTypeRepository.findAll());
        defaultSetValues.put(DefaultSetEnum.PROPERTY_TYPE.name(),propertyTypeRepository.findAll());


        return defaultSetValues;
    }

}
