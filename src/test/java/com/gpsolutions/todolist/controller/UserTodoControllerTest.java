package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.UserTodoController.USER_TODO_API;
import static com.gpsolutions.todolist.data.TestData.ADMIN_ENC;
import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_ENC;
import static com.gpsolutions.todolist.data.TestData.USER_LIST;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.service.TodoService;
import com.gpsolutions.todolist.util.NotFoundException;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class UserTodoControllerTest extends AbstractControllerTest {

    @MockBean
    private TodoService todoService;

    @Before
    public void authorize() throws Exception {
        given(userDetailsService.loadUserByUsername(isA(String.class))).willReturn(USER_ENC);

        super.authorize(USER);
    }

    @Test
    public void testGet() throws Exception {
        given(todoService.get(anyInt(), anyInt())).willReturn(USER_LIST);

        mockMvc.perform(get("/api/user/todo/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name", is(USER_LIST.getName())));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetNotFound() throws Exception {
        given(todoService.get(anyInt(), anyInt())).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/user/todo/99")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isNotFound())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.error").exists());

    }

    @Test
    public void testCreate() throws Exception {
        val todoList = new TodoList("new list name", USER);
        given(todoService.create(isA(User.class), isA(TodoList.class))).willReturn(todoList);

        mockMvc.perform(post(USER_TODO_API)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .content(toJson(todoList)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name", is(todoList.getName())));
    }

}
