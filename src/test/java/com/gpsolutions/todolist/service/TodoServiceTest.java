package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_LIST;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import java.util.List;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = "ADMIN")
public class TodoServiceTest extends AbstractServiceTest {

    @Autowired
    private TodoService todoService;

    @Test
    public void testGetAllByUser() {
        val todoLists = todoService.getAll(USER.getId());
        Assert.assertFalse(todoLists.isEmpty());
        todoLists.forEach(tl -> Assert.assertFalse(tl.getItems().isEmpty()));
    }

    @Test
    public void testCreate() {
        TodoList todoList = new TodoList("newlist", null);
        todoList = todoService.create(USER, todoList);

        List<TodoList> lists = todoService.getAll(USER.getId());
        Assert.assertNotNull(todoList.getOwner());
        Assert.assertEquals(2, lists.size());
    }

    @Test
    public void testUpdate() {
        val todoList = new TodoList();
        todoList.setId(USER_LIST.getId());
        todoList.setName("new name");
        todoList.setOwner(USER_LIST.getOwner());
        todoList.setItems(USER_LIST.getItems());

        todoService.update(USER.getId(), todoList);

        val updated = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertNotEquals(USER_LIST, updated);
        Assert.assertEquals("new name", updated.getName());
    }

    @Test
    public void testSaveItem() {
        val item = new TodoItem("newitem");
        todoService.saveItem(USER.getId(), USER_LIST.getId(), item);

        val list = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertEquals(3, list.getItems().size());
    }

    @Test
    public void testDeleteItem() {
        todoService.deleteItem(USER.getId(), USER_LIST.getId(), 3);

        val list = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertEquals(1, list.getItems().size());
    }

    @Test
    public void testGet() {
        val list = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertEquals(2, list.getItems().size());
    }

}
