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

/**
 * TodoList REST controller class. Represents operations that are available to regular user
 */
@RestController
@RequestMapping(value = USER_TODO_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTodoController extends TodoController {

    public static final String USER_TODO_API = "/api/user/todo";

    /**
     * Endpoint to obtain all the TodoList entities owned by authorized user.
     *
     * @param authentication holds authorized user
     * @return collection of TodoList entities
     */
    @GetMapping
    public List<TodoList> getAll(final Authentication authentication) {
        return super.getAll(getPrincipalId(authentication));
    }

    /**
     * Endpoint to get specific TodoList object by its identifier owned by authorized user.
     *
     * @param id identifier of TodoList
     * @param authentication holds authorized user
     * @return TodoList object with the specified identifier
     */
    @GetMapping("/{id}")
    public TodoList get(@PathVariable("id") final int id, final Authentication authentication) {
        return super.get(getPrincipalId(authentication), id);
    }

    /**
     * Endpoint to save new TodoList to database with authorized user as its owner.
     *
     * @param todoList TodoList object to be persisted
     * @param authentication holds authorized user
     * @return persisted TodoList entity
     */
    @PostMapping
    public TodoList create(@RequestBody final TodoList todoList,
        final Authentication authentication) {
        System.out.println(todoList);
        return super.create((User) authentication.getPrincipal(), todoList);
    }

    /**
     * Endpoint to update existing TodoList owned by authorized user.
     *
     * @param listId TodoList identifier
     * @param todoList TodoList object to be merged with existing
     * @param authentication holds authorized user
     * @return updated TodoList entity
     */
    @PutMapping("/{id}")
    public TodoList update(@PathVariable("id") final int listId,
        @RequestBody final TodoList todoList,
        Authentication authentication) {
        return super.update(getPrincipalId(authentication), listId, todoList);
    }

    /**
     * Endpoint to remove existing TodoList with all dependent items that is owned by authorized
     * user
     *
     * @param id TodoList identifier
     * @param authentication holds authorized user
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final int id, final Authentication authentication) {
        super.delete(getPrincipalId(authentication), id);
    }

    /**
     * Endpoint to modify TodoList's embedded items collection by adding new TodoItem. TodoList must
     * be owned by authorized user.
     *
     * @param id TodoList identifier
     * @param item TodoItem to be added to TodoList's embedded collection
     * @param authentication holds authorized user
     * @return modified TodoList entity
     */
    @PostMapping("/{id}/item")
    public TodoList addItem(@PathVariable("id") final int id, @RequestBody final TodoItem item,
        Authentication authentication) {
        return super.addItem(getPrincipalId(authentication), id, item);
    }

    /**
     * Endpoint to modify specific TodoList's item with the given item identifier. TodoList must be
     * owned by authorized user.
     *
     * @param id TodoList identifier
     * @param itemId identifier of item to be modified
     * @param item TodoItem object to be merged with the existing one
     * @param authentication holds authorized user
     * @return modified TodoList entity
     */
    @PutMapping("/{id}/item/{itemId}")
    public TodoList updateItem(@PathVariable("id") final int id,
        @PathVariable("itemId") final int itemId,
        @RequestBody TodoItem item, Authentication authentication) {
        item.setId(itemId);
        return super.updateItem(getPrincipalId(authentication), id, itemId, item);
    }

    /**
     * Endpoint to remove item with the given identifier from TodoList owned by authorized user
     *
     * @param id TodoList identifier
     * @param itemId identifier of item to be removed
     * @param authentication holds authorized user
     * @return modified TodoList entity
     */
    @DeleteMapping("/{id}/item/{itemId}")
    public TodoList deleteItem(@PathVariable("id") final int id,
        @PathVariable("itemId") final int itemId,
        Authentication authentication) {
        return super.deleteItem(getPrincipalId(authentication), id, itemId);
    }

    private int getPrincipalId(final Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }

}
