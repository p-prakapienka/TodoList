package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.model.User.ROLE_ADMIN;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.List;
import org.springframework.security.access.annotation.Secured;

public interface TodoService {

    List<TodoList> getAll(int userId);

    TodoList get(int userId, int listId);

    TodoList create(User user, TodoList list);

    @Secured(ROLE_ADMIN)
    TodoList update(int userId, TodoList list);

    void delete(int userId, int listId);

    TodoList saveItem(int userId, int listId, TodoItem item);

    TodoList deleteItem(int userId, int listId, int itemId);

}
