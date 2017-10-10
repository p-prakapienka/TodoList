package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.data.TestData.ADMIN;
import static com.gpsolutions.todolist.data.TestData.ADMIN_ENC;
import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_ENC;
import static com.gpsolutions.todolist.model.User.ROLE_ADMIN;
import static com.gpsolutions.todolist.model.User.ROLE_USER;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.util.NotFoundException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = ROLE_ADMIN)
@Slf4j
public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testCreate() {
        val password = "newpassword";
        val newUser = new User("newuser", password,
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());

        val created = userService.create(newUser);

        Assert.assertTrue(encoder.matches(password, created.getPassword()));

        val users = userService.getAll();
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void testGetAll() {
        val users = userService.getAll();
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void testGet() {
        val user = userService.get(ADMIN.getId());
        Assert.assertEquals(ADMIN_ENC, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        userService.get(Integer.MAX_VALUE);
    }

    @Test
    public void testDelete() {
        userService.delete(USER.getId());

        val users = userService.getAll();
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testLoadUserByUserName() {
        val user = userDetailsService.loadUserByUsername(USER.getUsername());
        Assert.assertEquals(USER, user);
    }

}
