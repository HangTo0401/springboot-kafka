package fr.alex.service;

import fr.alex.domain.Account;
import fr.alex.domain.Activity;
import fr.alex.domain.Preference;
import fr.alex.domain.User;
import fr.alex.io.LineMessage;
import fr.alex.repository.*;
import fr.alex.validation.NoGenderMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class LineService {

    Logger logger = LoggerFactory.getLogger(LineService.class);

    private final UserRepository userRepository;
    private final GenderRepository genderRepository;
    private final CountryRepository countryRepository;
    private final ActivityRepository activityRepository;
    private final PreferenceRepository preferenceRepository;
    private final AccountRepository accountRepository;

    public LineService(UserRepository userRepository, GenderRepository genderRepository, CountryRepository countryRepository, ActivityRepository activityRepository, PreferenceRepository preferenceRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.genderRepository = genderRepository;
        this.countryRepository = countryRepository;
        this.activityRepository = activityRepository;
        this.preferenceRepository = preferenceRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void saveLine(LineMessage lineMessage) {
        //Validation
        validateGender(lineMessage);

        //Mapping
        User user = mapUser(lineMessage);
        userRepository.saveAndFlush(user);

        Activity activity = mapActivity(lineMessage, user);
        activityRepository.save(activity);

        Preference preference = mapPreference(lineMessage, user);
        preferenceRepository.save(preference);

        Account account = mapAccount(lineMessage, user);
        accountRepository.save(account);

        logger.info("Line id: {} saved", lineMessage.getId());
    }

    private void validateGender(LineMessage lineMessage) {
        if (!genderRepository.existsByLabel(lineMessage.getGender())) {
            throw new NoGenderMatchException("No gender match label: " + lineMessage.getGender());
        }
    }

    private Preference mapPreference(LineMessage lineMessage, User user) {
        Preference preference = user.getPreference() == null ? new Preference() : user.getPreference();
        preference.setUser(user);
        preference.setFavoriteColor(lineMessage.getFavoriteColor());
        user.setPreference(preference);
        return preference;
    }

    private Account mapAccount(LineMessage lineMessage, User user) {
        Account account = user.getAccount() == null ? new Account() : user.getAccount();
        account.setUser(user);
        account.setBalance(lineMessage.getAccountBalance());
        user.setAccount(account);
        return account;
    }

    private Activity mapActivity(LineMessage lineMessage, User user) {
        Activity activity = activityRepository.findByLastLoginAndUser(lineMessage.getLastLogin(), user).orElse(new Activity());
        activity.setUser(user);
        activity.setIpAddress(lineMessage.getIpAddress());
        activity.setLastLogin(lineMessage.getLastLogin());
        user.getActivities().add(activity);
        return activity;
    }

    private User mapUser(LineMessage lineMessage) {
        User user = userRepository.findById(lineMessage.getId()).orElse(new User());
        user.setId(lineMessage.getId());
        user.setFirstName(lineMessage.getFirstName());
        user.setLastName(lineMessage.getLastName());
        user.setEmail(lineMessage.getEmail());

        user.setGender(genderRepository.findByLabel(lineMessage.getGender()));
        user.setCountry(countryRepository.findByCode(lineMessage.getCountryIsoCode()));
        return user;
    }
}
