package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.model.User.ROLE_ADMIN;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.List;
import org.springframework.security.access.annotation.Secured;

public interface TodoService {

    List<TodoList> getAll(User user);

    @Secured(ROLE_ADMIN)
    List<TodoList> getAll(int userId);

    TodoList get(User user, int listId);

    @Secured(ROLE_ADMIN)
    TodoList get(int userId, int listId);

    TodoList save(User user, TodoList list);

    @Secured(ROLE_ADMIN)
    TodoList update(int userId, TodoList list);

    void delete(User user, int listId);

    @Secured(ROLE_ADMIN)
    void delete(int userId, int listId);

    TodoList saveItem(User user, int listId, TodoItem item);

    @Secured(ROLE_ADMIN)
    TodoList saveItem(int userId, int listId, TodoItem item);

    TodoList deleteItem(User user, int listId, int itemId);

    @Secured(ROLE_ADMIN)
    TodoList deleteItem(int userId, int listId, int itemId);

}
