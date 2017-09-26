package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.repository.TodoRepository;
import com.gpsolutions.todolist.util.ExceptionUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<TodoList> getAll(User user) {
        return todoRepository.findByOwnerId(user.getId());
    }

    @Override
    public List<TodoList> getAll(int userId) {
        return todoRepository.findByOwnerId(userId);
    }

    @Override
    public TodoList get(User user, int listId) {
        TodoList list = todoRepository.findByIdAndOwnerId(listId, user.getId());
        ExceptionUtil.checkNotNull(listId, list, TodoList.class);
        return list;
    }

    @Override
    public TodoList get(int userId, int listId) {
        TodoList list = todoRepository.findByIdAndOwnerId(listId, userId);
        ExceptionUtil.checkNotNull(listId, list, TodoList.class);
        return list;
    }

    @Override
    @Transactional
    public TodoList save(User user, TodoList list) {
        if (!list.isNew()) {
            TodoList original = get(user, list.getId());

            original.setName(list.getName());
            return todoRepository.save(original);
        }
        list.setOwner(user);
        return todoRepository.save(list);
    }

    @Override
    @Transactional
    public TodoList update(int userId, TodoList list) {
        TodoList original = get(userId, list.getId());
        original.setName(list.getName());
        return todoRepository.save(original);
    }

    @Override
    public void delete(User user, int listId) {
        todoRepository.deleteByIdAndOwnerId(listId, user.getId());
    }

    @Override
    public void delete(int userId, int listId) {
        todoRepository.deleteByIdAndOwnerId(listId, userId);
    }

    @Override
    @Transactional
    public TodoList saveItem(User user, int listId, final TodoItem item) {
        TodoList list = get(user, listId);
        return saveItem(list, item);
    }

    @Override
    @Transactional
    public TodoList saveItem(int userId, int listId, TodoItem item) {
        TodoList list = get(userId, listId);
        return saveItem(list, item);
    }

    @Override
    public TodoList deleteItem(User user, int listId, int itemId) {
        TodoList list = get(user, listId);
        TodoItem item = findItem(list, itemId);
        list.getItems().remove(item);
        return todoRepository.save(list);
    }

    @Override
    public TodoList deleteItem(int userId, int listId, int itemId) {
        TodoList list = get(userId, listId);
        TodoItem item = findItem(list, itemId);
        list.getItems().remove(item);
        return todoRepository.save(list);
    }

    private TodoList saveItem(TodoList list, TodoItem item) {
        if (item.isNew()) {
            list.getItems().add(item);
        } else {
            TodoItem todoItem = findItem(list, item.getId());
            todoItem.setDescription(item.getDescription());
            todoItem.setDone(item.isDone());
        }
        return todoRepository.save(list);
    }

    private TodoItem findItem(TodoList list, int itemId) {
        return list.getItems()
            .stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> ExceptionUtil.getNotFoundException(itemId, TodoItem.class));
    }

}
