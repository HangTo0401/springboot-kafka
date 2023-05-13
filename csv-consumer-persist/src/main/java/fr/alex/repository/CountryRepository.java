package fr.alex.repository;

import fr.alex.domain.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CountryRepository extends CrudRepository<Country, UUID> {

    boolean existsByCode(String code);
    Country findByCode(String code);
}
