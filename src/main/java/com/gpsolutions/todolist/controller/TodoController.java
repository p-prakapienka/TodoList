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

    protected List<TodoList> getAll(final int userId) {
        return todoService.getAll(userId);
    }

    protected TodoList get(final int userId, final int id) {
        return todoService.get(userId, id);
    }

    protected TodoList create(final User user, final TodoList todoList) {
        return todoService.create(user, todoList);
    }

    protected TodoList update(final int userId, final int listId, final TodoList todoList) {
        todoList.setId(listId);
        return todoService.update(userId, todoList);
    }

    protected void delete(final int userId, final int listId) {
        todoService.delete(userId, listId);
    }

    protected TodoList addItem(final int userId, final int listId, final TodoItem item) {
        return todoService.saveItem(userId, listId, item);
    }

    protected TodoList updateItem(final int userId, final int listId, final int itemId,
        final TodoItem item) {
        item.setId(itemId);
        return todoService.saveItem(userId, listId, item);
    }

    protected TodoList deleteItem(final int userId, final int listId, final int itemId) {
        return todoService.deleteItem(userId, listId, itemId);
    }

}
