package com.gpsolutions.todolist.service;

import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_LIST;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import java.util.List;
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
    public void testCreate() {
        TodoList todoList = new TodoList("newlist", null);
        todoList = todoService.create(USER, todoList);

        List<TodoList> lists = todoService.getAll(USER.getId());
        Assert.assertNotNull(todoList.getOwner());
        Assert.assertEquals(2, lists.size());
    }

    @Test
    public void testSaveItem() {
        TodoItem item = new TodoItem("newitem");
        todoService.saveItem(USER.getId(), USER_LIST.getId(), item);

        TodoList list = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertEquals(3, list.getItems().size());
    }

    @Test
    public void testDeleteItem() {
        todoService.deleteItem(USER.getId(), USER_LIST.getId(), 3);

        TodoList list = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertEquals(1, list.getItems().size());
    }

    @Test
    public void testGet() {
        TodoList list = todoService.get(USER.getId(), USER_LIST.getId());
        Assert.assertEquals(2, list.getItems().size());
    }

}