package com.gpsolutions.todolist.repository;

import static com.gpsolutions.todolist.data.TestData.ADMIN;
import static com.gpsolutions.todolist.data.TestData.ADMIN_LIST;
import static com.gpsolutions.todolist.data.TestData.USER;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TodoRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TodoRepository repository;

    @Test
    public void testGet() {
        val todos = repository.findByOwnerId(USER.getId());

        Assert.assertEquals(1, todos.size());
        Assert.assertEquals(2, todos.get(0).getItems().size());
    }

    @Test
    public void testInsertItem() {
        val todo = repository.findByIdAndOwnerId(ADMIN_LIST.getId(), ADMIN.getId());

        Assert.assertNotNull(todo);
        todo.getItems().add(new TodoItem("Test item"));
        repository.save(todo);

        val updated = repository.findByIdAndOwnerId(ADMIN_LIST.getId(), ADMIN.getId());
        Assert.assertEquals(3, updated.getItems().size());
    }

    @Test
    public void testDeleteItem() {
        val todo = repository.findByIdAndOwnerId(ADMIN_LIST.getId(), ADMIN.getId());

        Assert.assertNotNull(todo);
        todo.getItems().remove(todo.getItems().stream().findFirst().get());
        repository.save(todo);

        val updated = repository.findByIdAndOwnerId(ADMIN_LIST.getId(), ADMIN.getId());
        Assert.assertEquals(1, updated.getItems().size());
    }

}
