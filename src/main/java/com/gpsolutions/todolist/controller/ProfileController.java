package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.ProfileController.PROFILE_API;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.service.UserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller class. Represents operations on authorized user.
 */
@RestController
@RequestMapping(PROFILE_API)
public class ProfileController {

    public static final String PROFILE_API = "/api/profile";

    @Autowired
    private UserService userService;

    /**
     * Endpoint to obtain authorized user information.
     *
     * @param authentication hold authorized user
     * @return authorized user object without password
     */
    @GetMapping
    public User profile(final Authentication authentication) {
        return userService.get(((User) authentication.getPrincipal()).getId());
    }

    /**
     * Endpoint to save new user. No auth required.
     *
     * @param user User to be saved to database
     * @return persisted User entity
     */
    @PostMapping("/register")
    public User register(@RequestBody final User user) {
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        return userService.create(user);
    }

    /**
     * Endpoint to modify authorized user information.
     *
     * @param user User object to be merged with the existing one
     * @param authentication holds authorized User
     * @return merged User entity
     */
    @PutMapping
    public User update(@RequestBody final User user, final Authentication authentication) {
        final User principal = (User) authentication.getPrincipal();
        user.setRoles(principal.getRoles().contains(Role.ROLE_ADMIN)
            ? user.getRoles() : principal.getRoles());
        final Integer userId = (principal).getId();
        return userService.update(userId, user);
    }

}
