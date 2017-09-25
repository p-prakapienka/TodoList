package com.gpsolutions.todolist.repository;

import com.gpsolutions.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
