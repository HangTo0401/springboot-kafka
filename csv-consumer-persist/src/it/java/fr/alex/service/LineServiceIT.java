package fr.alex.service;

import fr.alex.config.InitConfig;
import fr.alex.config.RepositoryConfig;
import fr.alex.config.ServiceConfig;
import fr.alex.domain.Activity;
import fr.alex.domain.User;
import fr.alex.init.CountryInit;
import fr.alex.init.GenderInit;
import fr.alex.io.LineMessage;
import fr.alex.repository.ActivityRepository;
import fr.alex.repository.UserRepository;
import fr.alex.validation.NoGenderMatchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@Import({RepositoryConfig.class, ServiceConfig.class, InitConfig.class})
public class LineServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private LineService lineService;

    @Autowired
    private GenderInit genderInit;

    @Autowired
    private CountryInit countryInit;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void saveLine_persist() {
        //Given
        ZonedDateTime lastLogin = ZonedDateTime.of(2018, 9, 22, 15, 20, 10, 0, ZoneId.systemDefault());
        String firstName = "Alex";
        String lastName = "Bl";
        String email = "alex.bl@laposte.net";
        String ipAddress = "169.77.92.179";
        String favoriteColor = "#506128";
        String gender = "Male";
        String countryCode = "FR";
        BigDecimal accountBalance = new BigDecimal("100.00");

        LineMessage lineMessage = new LineMessage();
        lineMessage.setId(1L);
        lineMessage.setFirstName(firstName);
        lineMessage.setLastName(lastName);
        lineMessage.setIpAddress(ipAddress);
        lineMessage.setLastLogin(lastLogin);
        lineMessage.setFavoriteColor(favoriteColor);
        lineMessage.setGender(gender);
        lineMessage.setAccountBalance(accountBalance);
        lineMessage.setCountryIsoCode(countryCode);
        lineMessage.setEmail(email);

        genderInit.init();
        countryInit.init();

        //When
        lineService.saveLine(lineMessage);

        entityManager.flush();
        entityManager.clear();

        //Then
        User actual = userRepository.getOne(1L);
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getActivities())
                .extracting(Activity::getIpAddress, Activity::getLastLogin)
                .containsOnly(tuple(ipAddress, lastLogin));
        assertThat(actual.getGender().getLabel()).isEqualTo(gender);
        assertThat(actual.getCountry().getCode()).isEqualTo(countryCode);
        assertThat(actual.getPreference().getFavoriteColor()).isEqualTo(favoriteColor);
        assertThat(actual.getAccount().getBalance()).isEqualTo(accountBalance);
    }

    @Test
    public void saveLine_throwException_whenGenderDoNotMatch() {
        //Given
        String gender = "NoMatch";

        LineMessage lineMessage = new LineMessage();
        lineMessage.setId(1L);
        lineMessage.setGender(gender);

        genderInit.init();

        //When
        assertThatThrownBy(() -> lineService.saveLine(lineMessage))
                .isInstanceOf(NoGenderMatchException.class);
    }

}



