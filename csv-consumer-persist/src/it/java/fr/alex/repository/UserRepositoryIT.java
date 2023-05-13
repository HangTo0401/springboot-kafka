package fr.alex.repository;

import fr.alex.config.RepositoryConfig;
import fr.alex.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@Import(RepositoryConfig.class)
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindById_thenReturnUser() {
        // given
        User user = new User();
        user.setId(1L);
        user.setFirstName("Alex");
        user.setLastName("Bl");
        userRepository.saveAndFlush(user);

        // when
        Optional<User> found = userRepository.findById(1L);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Alex");
    }

}
