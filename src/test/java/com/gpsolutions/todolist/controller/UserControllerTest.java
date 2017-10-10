package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.UserController.USER_API;
import static com.gpsolutions.todolist.data.TestData.ADMIN;
import static com.gpsolutions.todolist.data.TestData.ADMIN_ENC;
import static com.gpsolutions.todolist.data.TestData.USER;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.User;
import java.util.Arrays;
import java.util.Collections;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

public class UserControllerTest extends AbstractControllerTest {

    @Before
    public void authorize() throws Exception {
        given(userDetailsService.loadUserByUsername(isA(String.class))).willReturn(ADMIN_ENC);

        super.authorize(ADMIN);
    }

    @Test
    public void testGetAll() throws Exception {
        given(userService.getAll()).willReturn(Arrays.asList(ADMIN, USER));

        mockMvc
            .perform(get(USER_API)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()", is(2)));

    }

    @Test
    public void testGet() throws Exception {
        given(userService.get(anyInt())).willReturn(USER);

        mockMvc
            .perform(get(USER_API + "/" + USER.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(USER.getUsername())))
            .andExpect(jsonPath("$.password").doesNotExist());

    }

    @Test
    public void testCreate() throws Exception {
        val user = new User("new name", "password",
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());
        given(userService.create(isA(User.class))).willReturn(user);

        mockMvc
            .perform(post(USER_API)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            .content(toJson(user)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void testUpdate() throws Exception {
        val updated = new User(USER.getId(), "new name", USER.getPassword(),
            USER.getRoles(), USER.getTodoLists());
        given(userService.update(anyInt(), isA(User.class))).willReturn(updated);

        mockMvc
            .perform(put(USER_API + "/" + USER.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .content(toJson(updated)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(updated.getUsername())));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc
            .perform(delete(USER_API + "/" + USER.getId())
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk());

        verify(userService).delete(isA(Integer.class));
    }

}
