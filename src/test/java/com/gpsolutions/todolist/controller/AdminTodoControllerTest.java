package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.AdminTodoController.ADMIN_TODO_API;
import static com.gpsolutions.todolist.data.TestData.ADMIN;
import static com.gpsolutions.todolist.data.TestData.ADMIN_ENC;
import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_ENC;
import static com.gpsolutions.todolist.data.TestData.USER_LIST;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.service.TodoService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class AdminTodoControllerTest extends AbstractControllerTest {

    @MockBean
    private TodoService todoService;

    @Test
    public void testUserNotAllowedToAccessApi() throws Exception {
        given(userDetailsService.loadUserByUsername(isA(String.class))).willReturn(USER_ENC);
        super.authorize(USER);

        mockMvc.perform(get(ADMIN_TODO_API, ADMIN.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAllForUser() throws Exception {
        authorizeAdmin();

        given(todoService.getAll(anyInt())).willReturn(Collections.singletonList(USER_LIST));

        mockMvc.perform(get(ADMIN_TODO_API, USER.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()", is(1)))
            .andExpect(jsonPath("$[0].name", is(USER_LIST.getName())));
    }

    @Test
    public void testGet() throws Exception {
        authorizeAdmin();

        given(todoService.get(anyInt(), anyInt())).willReturn(USER_LIST);

        mockMvc.perform(get(ADMIN_TODO_API + "/list/{listId}", USER.getId(), USER_LIST.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name", is(USER_LIST.getName())))
            .andExpect(jsonPath("$.items.length()", is(USER_LIST.getItems().size())));
    }

    @Test
    public void testUpdate() throws Exception {
        authorizeAdmin();

        val updated = new TodoList("newlist", USER_LIST.getOwner(), USER_LIST.getItems());
        updated.setId(USER_LIST.getId());

        given(todoService.update(anyInt(), isA(TodoList.class))).willReturn(updated);

        mockMvc.perform(put(ADMIN_TODO_API + "/list/{listId}", USER.getId(), USER_LIST.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            .content(toJson(new TodoList("newlist", USER))))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(updated.getName())))
            .andExpect(jsonPath("$.items.length()", is(2)));
    }

    @Test
    public void testDelete() throws Exception {
        authorizeAdmin();

        mockMvc.perform(delete(ADMIN_TODO_API + "/list/{listId}", USER.getId(), USER_LIST.getId())
            .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk());

        verify(todoService).delete(anyInt(), anyInt());
    }

    @Test
    public void testAddItem() throws Exception {
        authorizeAdmin();

        val updated = new TodoList(USER_LIST.getId(), USER_LIST.getName(), USER);
        val newItem = new TodoItem("newitem");
        List<TodoItem> items = new ArrayList<>();
        items.add(newItem);
        items.addAll(USER_LIST.getItems());
        updated.setItems(items);

        given(todoService.saveItem(anyInt(), anyInt(), isA(TodoItem.class))).willReturn(updated);

        mockMvc.perform(post(ADMIN_TODO_API + "/list/{listId}/item", USER.getId(),
            USER_LIST.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            .content(toJson(newItem)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.length()", is(3)));
    }

    @Test
    public void testUpdateItem() throws Exception {
        authorizeAdmin();

        val updated = new TodoList(USER_LIST.getId(), USER_LIST.getName(), USER);
        updated.setItems(USER_LIST.getItems().stream()
            .map(i -> new TodoItem(i.getId(), i.getDescription(), i.isDone()))
            .collect(Collectors.toList()));
        val updatedItem = updated.getItems().get(1);
        updatedItem.setDone(true);

        given(todoService.saveItem(anyInt(), anyInt(), isA(TodoItem.class))).willReturn(updated);

        mockMvc.perform(put(ADMIN_TODO_API + "/list/{listId}/item/{itemId}", USER.getId(),
            USER_LIST.getId(), updatedItem.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            .content(toJson(updatedItem)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.length()", is(2)))
            .andExpect(jsonPath("$.items[1].done", is(true)));
    }

    @Test
    public void testDeleteItem() throws Exception {
        authorizeAdmin();

        val itemToRemove = USER_LIST.getItems().get(0);
        val updated = new TodoList(USER_LIST.getId(), USER_LIST.getName(), USER);
        updated.setItems(USER_LIST.getItems().stream()
            .filter(i -> i.getId().equals(itemToRemove.getId()))
            .collect(Collectors.toList()));
        given(todoService.deleteItem(anyInt(), anyInt(), anyInt()))
            .willReturn(updated);

        mockMvc.perform(delete(ADMIN_TODO_API + "/list/{listId}/item/{itemId}", USER.getId(),
            USER_LIST.getId(), itemToRemove.getId())
            .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.length()", is(1)));
    }

    private void authorizeAdmin() throws Exception {
        given(userDetailsService.loadUserByUsername(anyString())).willReturn(ADMIN_ENC);
        authorize(ADMIN);
    }

}
