package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.UserController.USER_API;
import static com.gpsolutions.todolist.data.TestData.ADMIN;
import static com.gpsolutions.todolist.data.TestData.ADMIN_ENC;
import static com.gpsolutions.todolist.data.TestData.USER;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
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
        given(userService.get(USER.getId())).willReturn(USER);

        mockMvc
            .perform(get(USER_API + "/" + USER.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(USER.getUsername())));
    }

    @Test
    public void testSave() throws Exception {

    }

}
