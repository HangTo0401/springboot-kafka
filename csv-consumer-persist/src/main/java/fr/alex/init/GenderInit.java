package fr.alex.init;

import fr.alex.domain.Gender;
import fr.alex.repository.GenderRepository;
import org.springframework.transaction.annotation.Transactional;

public class GenderInit {

    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    private final GenderRepository genderRepository;

    public GenderInit(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Transactional
    public void init() {
        if(!genderRepository.existsByLabel(MALE)) {
            genderRepository.save(new Gender(MALE));
        }
        if(!genderRepository.existsByLabel(FEMALE)) {
            genderRepository.save(new Gender(FEMALE));
        }

    }
}
