package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.User;
import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(int id);

    User save(User user);

    void delete(int id);

}
