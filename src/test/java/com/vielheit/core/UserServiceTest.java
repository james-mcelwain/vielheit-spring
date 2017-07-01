package com.vielheit.core;

import com.vielheit.CoreApplication;
import com.vielheit.core.domain.User;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = CoreApplication.class)
@Profile("test")
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() throws ApplicationException {
        final String email = "test@test.com";
        final String name = "test";
        final String pw = "password";

        User user = new User();
        user.setEmailAddress(email);
        user.setFirstName(name);
        user.setLastName(name);
        user.setPassword(pw);

        userService.create(user);

        assertEquals(user.getEmailAddress(), email);
        assertEquals(user.getFirstName(), name);
        assertEquals(user.getLastName(), name);
        assertNotEquals(user.getPassword(), pw);
        assertNotNull(user.getRoles());
    }

}
