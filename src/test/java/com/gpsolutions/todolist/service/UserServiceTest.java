package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.data.TestData.ADMIN;
import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.model.User.ROLE_ADMIN;
import static com.gpsolutions.todolist.model.User.ROLE_USER;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.User;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = ROLE_ADMIN)
@Slf4j
public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(authorities = ROLE_USER)
    public void testMethodSecurity() {
        userService.getAll();
    }

    @Test
    public void testCreate() {
        String password = "newpassword";
        User newUser = new User("newuser", password,
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());

        User created = userService.create(newUser);

        Assert.assertTrue(encoder.matches(password, created.getPassword()));

        List<User> users = userService.getAll();
        Assert.assertEquals(3, users.size());
    }

}
