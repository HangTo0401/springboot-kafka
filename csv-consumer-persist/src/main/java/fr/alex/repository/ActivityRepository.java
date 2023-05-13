package fr.alex.repository;

import fr.alex.domain.Activity;
import fr.alex.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository extends CrudRepository<Activity, UUID> {
    Optional<Activity> findByLastLoginAndUser(ZonedDateTime zonedDateTime, User user);
}
