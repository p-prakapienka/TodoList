package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.List;

public interface TodoService {

    List<TodoList> getAll(int userId);

    TodoList get(int userId, int listId);

    TodoList create(User user, TodoList list);

    TodoList update(int userId, TodoList list);

    void delete(int userId, int listId);

    TodoList saveItem(int userId, int listId, TodoItem item);

    TodoList deleteItem(int userId, int listId, int itemId);

}
