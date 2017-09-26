package com.gpsolutions.todolist.repository;

import com.gpsolutions.todolist.model.TodoList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoList, Integer> {

    List<TodoList> findByOwnerId(int ownerId);

    TodoList findByIdAndOwnerId(int id, int ownerId);

    void deleteByIdAndOwnerId(int id, int ownerId);
}
