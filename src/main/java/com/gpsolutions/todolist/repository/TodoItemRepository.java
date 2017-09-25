package com.gpsolutions.todolist.repository;

import com.gpsolutions.todolist.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {

}
