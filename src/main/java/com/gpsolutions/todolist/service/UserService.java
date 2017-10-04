package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.User;
import java.util.List;

/**
 * Describes CRUD operations on User
 */
public interface UserService {

    /**
     * Lists all the registered users
     *
     * @return collection of User objects
     */
    List<User> getAll();

    /**
     * Find user by unique identifier
     *
     * @param id user unique identifier
     * @return User object if found
     * @throws com.gpsolutions.todolist.util.NotFoundException if no registered user found
     */
    User get(int id);

    /**
     * Saves new user to database with the default role ROLE_USER and encodes user password
     *
     * @param user User object to be saved
     * @return persisted User entity
     */
    User create(User user);

    /**
     * Replaces existing User object with the provided one
     *
     * @param id existing user identifier
     * @param user User object to replace the existing one
     * @return merged User entity
     * @throws com.gpsolutions.todolist.util.NotFoundException if no user with such identifier
     */
    User update(int id, User user);

    /**
     * Removes user with the provided identifier and all of its dependent entities or does
     * nothing if no user found
     *
     * @param id identifier of user to be deleted
     */
    void delete(int id);

}
