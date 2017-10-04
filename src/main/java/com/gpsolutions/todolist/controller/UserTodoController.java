package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.UserTodoController.USER_TODO_API;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.List;
import org.springframework.http.MediaType;
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
@RequestMapping(value = USER_TODO_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTodoController extends TodoController {

    public static final String USER_TODO_API = "/api/user/todo";

    @GetMapping
    public List<TodoList> getAll(Authentication authentication) {
        return super.getAll(getPrincipalId(authentication));
    }

    @GetMapping("/{id}")
    public TodoList get(@PathVariable("id") int id, Authentication authentication) {
        return super.get(getPrincipalId(authentication), id);
    }

    @PostMapping
    public TodoList create(@RequestBody TodoList todoList, Authentication authentication) {
        System.out.println(todoList);
        return super.create((User)authentication.getPrincipal(), todoList);
    }

    @PutMapping("/{id}")
    public TodoList update(@PathVariable("id") int listId, @RequestBody TodoList todoList,
        Authentication authentication) {
        return super.update(getPrincipalId(authentication), listId, todoList);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id, Authentication authentication) {
        super.delete(getPrincipalId(authentication), id);
    }

    @PostMapping("/{id}/item")
    public TodoList addItem(@PathVariable("id") int id, @RequestBody TodoItem item,
        Authentication authentication) {
        return super.addItem(getPrincipalId(authentication), id, item);
    }

    @PutMapping("/{id}/item/{itemId}")
    public TodoList updateItem(@PathVariable("id") int id, @PathVariable("itemId") int itemId,
        @RequestBody TodoItem item, Authentication authentication) {
        item.setId(itemId);
        return super.updateItem(getPrincipalId(authentication), id, itemId, item);
    }

    @DeleteMapping("/{id}/item/{itemId}")
    public TodoList deleteItem(@PathVariable("id") int id, @PathVariable("itemId") int itemId,
        Authentication authentication) {
        return super.deleteItem(getPrincipalId(authentication), id, itemId);
    }

    private int getPrincipalId(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }

}
