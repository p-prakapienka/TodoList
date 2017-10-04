package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.AdminTodoController.ADMIN_TODO_API;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ADMIN_TODO_API)
public class AdminTodoController extends TodoController {

    public static final String ADMIN_TODO_API = "/api/admin/todo/user/{uid}";

    @GetMapping
    public List<TodoList> getAll(@PathVariable("uid") int userId) {
        return super.getAll(userId);
    }

    @GetMapping("/list/{listId}")
    public TodoList get(@PathVariable("listId") int listId, @PathVariable("uid") int userId) {
        return super.get(userId, listId);
    }

    @PutMapping("/list/{listId}")
    public TodoList update(@PathVariable("listId") int listId, @PathVariable("uid") int userId,
        @RequestBody TodoList todoList) {
        return super.update(userId, listId, todoList);
    }

    @DeleteMapping("/list/{listId}")
    public void delete(@PathVariable("listId") int listId, @PathVariable("uid") int userId) {
        super.delete(userId, listId);
    }

    @PostMapping("/list/{listId}/item")
    public TodoList addItem(@PathVariable("listId") int listId, @PathVariable("uid") int userId,
        @RequestBody TodoItem item) {
        return super.addItem(userId, listId, item);
    }

    @PutMapping("/list/{listId}/item/{itemId}")
    public TodoList updateItem(@PathVariable("listId") int listId, @PathVariable("uid") int userId,
        @PathVariable("itemId") int itemId, @RequestBody TodoItem item) {
        item.setId(itemId);
        return super.updateItem(userId, listId, itemId, item);
    }

    @DeleteMapping("/list/{listId}/item/{itemId}")
    public TodoList deleteItem(@PathVariable("listId") int listId, @PathVariable("uid") int userId,
        @PathVariable("itemId") int itemId) {
        return super.deleteItem(userId, listId, itemId);
    }

}
