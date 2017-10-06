package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.AdminTodoController.ADMIN_TODO_API;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TodoList REST controller class. Represents operations that are available to administrator
 */
@RestController
@RequestMapping(ADMIN_TODO_API)
public class AdminTodoController extends TodoController {

    public static final String ADMIN_TODO_API = "/api/admin/todo/user/{uid}";

    /**
     * Endpoint to obtain all the TodoList entities owned by specific user.
     *
     * @param userId TodoList collection owner identifier
     * @return collection of TodoList entities
     */
    @GetMapping
    public List<TodoList> getAll(@PathVariable("uid") final int userId) {
        return super.getAll(userId);
    }

    /**
     * Endpoint to get specific TodoList object by its identifier owned by specified user.
     *
     * @param listId identifier of TodoList
     * @param userId TodoList owner identifier
     * @return TodoList object with the specified identifier
     */
    @GetMapping("/list/{listId}")
    public TodoList get(@PathVariable("listId") final int listId,
        @PathVariable("uid") final int userId) {
        return super.get(userId, listId);
    }

    /**
     * Endpoint to update existing TodoList owned by specified user.
     *
     * @param listId TodoList identifier
     * @param todoList TodoList object to be merged with existing
     * @param userId TodoList owner identifier
     * @return updated TodoList entity
     */
    @PutMapping("/list/{listId}")
    public TodoList update(@PathVariable("listId") final int listId,
        @PathVariable("uid") final int userId,
        @RequestBody final TodoList todoList) {
        return super.update(userId, listId, todoList);
    }

    /**
     * Endpoint to remove existing TodoList with all dependent items that is owned by specified
     * user
     *
     * @param listId TodoList identifier
     * @param userId TodoList owner identifier
     */
    @DeleteMapping("/list/{listId}")
    public void delete(@PathVariable("listId") final int listId,
        @PathVariable("uid") final int userId) {
        super.delete(userId, listId);
    }

    /**
     * Endpoint to modify TodoList's embedded items collection by adding new TodoItem. TodoList must
     * be owned by specified user.
     *
     * @param listId TodoList identifier
     * @param item TodoItem to be added to TodoList's embedded collection
     * @param userId TodoList owner identifier
     * @return modified TodoList entity
     */
    @PostMapping("/list/{listId}/item")
    public TodoList addItem(@PathVariable("listId") final int listId,
        @PathVariable("uid") final int userId, @RequestBody final TodoItem item) {
        return super.addItem(userId, listId, item);
    }

    /**
     * Endpoint to modify specific TodoList's item with the given item identifier. TodoList must be
     * owned by specified user.
     *
     * @param listId TodoList identifier
     * @param itemId identifier of item to be modified
     * @param item TodoItem object to be merged with the existing one
     * @param userId TodoList owner identifier
     * @return modified TodoList entity
     */
    @PutMapping("/list/{listId}/item/{itemId}")
    public TodoList updateItem(@PathVariable("listId") final int listId,
        @PathVariable("uid") final int userId,
        @PathVariable("itemId") final int itemId,
        @RequestBody final TodoItem item) {
        item.setId(itemId);
        return super.updateItem(userId, listId, itemId, item);
    }

    /**
     * Endpoint to remove item with the given identifier from TodoList owned by specified user
     *
     * @param listId TodoList identifier
     * @param itemId identifier of item to be removed
     * @param userId TodoList owner identifier
     * @return modified TodoList entity
     */
    @DeleteMapping("/list/{listId}/item/{itemId}")
    public TodoList deleteItem(@PathVariable("listId") final int listId,
        @PathVariable("uid") final int userId, @PathVariable("itemId") final int itemId) {
        return super.deleteItem(userId, listId, itemId);
    }

}
