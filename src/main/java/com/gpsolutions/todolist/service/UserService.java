package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.model.User.ROLE_ADMIN;

import com.gpsolutions.todolist.model.User;
import java.util.List;
import org.springframework.security.access.annotation.Secured;

public interface UserService {

    @Secured(ROLE_ADMIN)
    List<User> getAll();

    @Secured(ROLE_ADMIN)
    User get(int id);

    User create(User user);

    User update(int id, User user);

    @Secured(ROLE_ADMIN)
    void delete(int id);

}
