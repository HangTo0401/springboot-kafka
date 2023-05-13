package fr.alex.repository;

import fr.alex.domain.Gender;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GenderRepository extends CrudRepository<Gender, UUID> {

    boolean existsByLabel(String label);
    Gender findByLabel(String label);

}
