package fr.alex.repository;

import fr.alex.domain.Preference;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PreferenceRepository extends CrudRepository<Preference, UUID> {
}
