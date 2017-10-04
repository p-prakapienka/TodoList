package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.List;

/**
 * Interface for CRUD operations on provided user's TodoList entities
 */
public interface TodoService {

    /**
     * Method retrieves all the TodoList's of the specified User
     *
     * @param userId id of the lists owner
     * @return collection of TodoList's or an empty list if no entities found
     */
    List<TodoList> getAll(int userId);

    /**
     * Method to get TodoList entity based on entity id and owner id
     *
     * @param userId id of TodoList owner
     * @param listId id of the entity
     * @return TodoList object
     * @throws com.gpsolutions.todolist.util.NotFoundException if no result for the specified params
     */
    TodoList get(int userId, int listId);

    /**
     * Method saves new TodoList to the database and sets its owner to the provided User
     *
     * @param user the User that owns TodoList
     * @param list TodoList object to be saved
     * @return persisted TodoList object
     */
    TodoList create(User user, TodoList list);

    /**
     * Method to merge provided TodoList object with the existing in database.
     *
     * @param userId TodoList owner id
     * @param list TodoList to merge with the existing one
     * @return original TodoList object merged with the provided one
     * @throws com.gpsolutions.todolist.util.NotFoundException if original TodoList with such id
     * does not exist
     */
    TodoList update(int userId, TodoList list);

    /**
     * Method removes TodoList with embedded TodoItems from database or does nothing if there is
     * no entity with the provided id and owner
     *
     * @param userId id of TodoList owner
     * @param listId id of TodoList entity
     */
    void delete(int userId, int listId);

    /**
     * Method updates embedded TodoItem collection of TodoList.
     *  - If provided item has an id property collection is searched for an item with
     *    the same id property or throws an exception if nothing was found. If found the existing
     *    item merged with provided.
     *  - If it has no id property TodoItem is just added to the embedded collection.
     * After TodoItem collection has been changed successfully TodoList is saved to database.
     *
     * @param userId the owner of TodoList
     * @param listId id of TodoList to contain the item
     * @param item TodoItem to be merged to collection
     * @return refreshed TodoList entity
     * @throws com.gpsolutions.todolist.util.NotFoundException if no TodoList found
     * @throws com.gpsolutions.todolist.util.NotFoundException if TodoList does not contain an item
     */
    TodoList saveItem(int userId, int listId, TodoItem item);

    /**
     * Method to remove TodoItem from the TodoList's embedded collection
     *
     * @param userId the owner of TodoList
     * @param listId id of TodoList to contain the item
     * @param itemId id of TodoItem that to be removed from TodoList
     * @return refreshed TodoList entity
     * @throws com.gpsolutions.todolist.util.NotFoundException if no TodoList found
     * @throws com.gpsolutions.todolist.util.NotFoundException if TodoList does not contain an item
     */
    TodoList deleteItem(int userId, int listId, int itemId);

}
