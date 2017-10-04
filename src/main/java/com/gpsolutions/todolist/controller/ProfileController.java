package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.ProfileController.PROFILE_API;

import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PROFILE_API)
public class ProfileController {

    public static final String PROFILE_API = "/api/profile";

    @Autowired
    private UserService userService;

    @GetMapping
    public User profile(Authentication authentication) {
        return userService.get(((User)authentication.getPrincipal()).getId());
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user, Authentication authentication) {
        Integer userId = ((User)authentication.getPrincipal()).getId();
        return userService.update(userId, user);
    }

}
