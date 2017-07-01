package com.vielheit.core;

import com.vielheit.CoreApplication;
import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by admin on 5/20/17.
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = CoreApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUser() {
        User user = new User();
        user.setActive(true);
        user.setEmailAddress("test@test.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword("password");
        userRepository.save(user);

        User foundUser = userRepository.findByEmailAddress(user.getEmailAddress()).orElseGet(User::new);
        assertEquals(foundUser.getEmailAddress(), user.getEmailAddress());
        assertEquals(foundUser.getId(), user.getId());
    }
}
