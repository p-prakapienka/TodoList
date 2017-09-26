package com.gpsolutions.todolist.controller;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.service.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TodoController {

    @Autowired
    private TodoService todoService;

    protected List<TodoList> getAll(int userId) {
        return todoService.getAll(userId);
    }

    protected TodoList get(int userId, int id) {
        return todoService.get(userId, id);
    }

    protected TodoList create(User user, TodoList todoList) {
        return todoService.create(user, todoList);
    }

    protected TodoList update(int userId, int listId, TodoList todoList) {
        todoList.setId(listId);
        return todoService.update(userId, todoList);
    }

    protected void delete(int userId, int listId) {
        todoService.delete(userId, listId);
    }

    protected TodoList addItem(int userId, int listId, TodoItem item) {
        return todoService.saveItem(userId, listId, item);
    }

    protected TodoList updateItem(int userId, int listId, int itemId, TodoItem item) {
        item.setId(itemId);
        return todoService.saveItem(userId, listId, item);
    }

    protected TodoList deleteItem(int userId, int listId, int itemId) {
        return todoService.deleteItem(userId, listId, itemId);
    }

}
