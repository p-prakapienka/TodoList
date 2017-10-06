package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.UserController.USER_API;

import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents ADMIN user endpoints to operate on persisted users. Restricted to administrator usage
 * only.
 */
@RestController
@RequestMapping(USER_API)
public class UserController {

    public static final String USER_API = "/api/admin/user";

    @Autowired
    private UserService userService;

    /**
     * Endpoint to view all registered users
     *
     * @return collection of User objects
     */
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    /**
     * Endpoint to get specific user information.
     *
     * @param id user identifier
     * @return User object if found
     */
    @GetMapping("/{id}")
    public User get(@PathVariable("id") final int id) {
        return userService.get(id);
    }

    /**
     * Endpoint to create new user
     *
     * @param user User object to be persisted
     * @return persisted User entity
     */
    @PostMapping
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    /**
     * Endpoint to modify existing user
     *
     * @param id user identifier
     * @param user User object to be merged with the existing one
     * @return merged User entity
     */
    @PutMapping("/{id}")
    public User update(@PathVariable("id") final int id, @RequestBody final User user) {
        return userService.update(id, user);
    }

    /**
     * Endpoint to remove existing user and all of its dependent entities
     *
     * @param id user identifier
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final int id) {
        userService.delete(id);
    }

}
