package com.gpsolutions.todolist.service;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.repository.TodoRepository;
import com.gpsolutions.todolist.util.ExceptionUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TodoList> getAll(final int userId) {
        return todoRepository.findByOwnerId(userId);
    }

    @Override
    public TodoList get(final int userId, int listId) {
        final TodoList list = todoRepository.findByIdAndOwnerId(listId, userId);
        ExceptionUtil.checkNotNull(listId, list, TodoList.class);
        return list;
    }

    @Override
    @Transactional
    public TodoList create(final User user, final TodoList list) {
        list.setOwner(user);
        return todoRepository.save(list);
    }

    @Override
    @Transactional
    public TodoList update(final int userId, final TodoList list) {
        final TodoList original = get(userId, list.getId());
        original.setName(list.getName());
        return todoRepository.save(original);
    }

    @Override
    @Transactional
    public void delete(final int userId, final int listId) {
        todoRepository.deleteByIdAndOwnerId(listId, userId);
    }

    @Override
    @Transactional
    public TodoList saveItem(final int userId, final int listId, final TodoItem item) {
        final TodoList list = get(userId, listId);
        return saveItem(list, item);
    }

    @Override
    public TodoList deleteItem(final int userId, final int listId, final int itemId) {
        final TodoList list = get(userId, listId);
        final TodoItem item = findItem(list, itemId);
        list.getItems().remove(item);
        return todoRepository.save(list);
    }

    private TodoList saveItem(final TodoList list, final TodoItem item) {
        if (item.isNew()) {
            list.getItems().add(item);
        } else {
            final TodoItem todoItem = findItem(list, item.getId());
            todoItem.setDescription(item.getDescription());
            todoItem.setDone(item.isDone());
        }
        final TodoList saved = todoRepository.saveAndFlush(list);
        em.refresh(saved);
        return saved;
    }

    private TodoItem findItem(final TodoList list, final int itemId) {
        return list.getItems()
            .stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> ExceptionUtil.getNotFoundException(itemId, TodoItem.class));
    }

}
