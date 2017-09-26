package com.gpsolutions.todolist.controller;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.security.Principal;
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
@RequestMapping("/api/user/todo")
public class UserTodoController extends TodoController {

    @GetMapping
    public List<TodoList> getAll(Authentication authentication) {
        return todoService.getAll((User) authentication.getPrincipal());
    }

    @GetMapping("/{id}")
    public TodoList get(@PathVariable("id") int id, Authentication authentication) {
        return todoService.get((User) authentication.getPrincipal(), id);
    }

    @PostMapping
    public TodoList create(@RequestBody TodoList todoList, Authentication authentication) {
        return todoService.save((User)authentication.getPrincipal(), todoList);
    }

    @PutMapping("/{id}")
    public TodoList update(@PathVariable("id") int id, @RequestBody TodoList todoList,
        Authentication authentication) {
        todoList.setId(id);
        return todoService.save((User)authentication.getPrincipal(), todoList);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id, Authentication authentication) {
        todoService.delete((User) authentication.getPrincipal(), id);
    }

    @PostMapping("/{id}/item")
    public TodoList addItem(@PathVariable("id") int id, @RequestBody TodoItem item,
        Authentication authentication) {
        return todoService.saveItem((User) authentication.getPrincipal(), id, item);
    }

    @PutMapping("/{id}/item/{itemId}")
    public TodoList updateItem(@PathVariable("id") int id, @PathVariable("itemId") int itemId,
        @RequestBody TodoItem item, Authentication authentication) {
        item.setId(itemId);
        return todoService.saveItem((User) authentication.getPrincipal(), id, item);
    }

    @DeleteMapping("/{id}/item/{itemId}")
    public TodoList deleteItem(@PathVariable("id") int id, @PathVariable("itemId") int itemId,
        Authentication authentication) {
        return todoService.deleteItem((User) authentication.getPrincipal(), id, itemId);
    }

}
