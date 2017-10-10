package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.controller.ProfileController.PROFILE_API;
import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_ENC;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.User;
import java.util.Collections;
import lombok.val;
import org.junit.Test;
import org.springframework.http.MediaType;

public class ProfileControllerTest extends AbstractControllerTest {


    @Test
    public void testGetProfile() throws Exception {
        given(userDetailsService.loadUserByUsername(isA(String.class))).willReturn(USER_ENC);
        super.authorize(USER);

        given(userService.get(anyInt())).willReturn(USER_ENC);

        mockMvc.perform(get(PROFILE_API)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(USER.getUsername())))
            .andExpect(jsonPath("$.password").doesNotExist());

    }

    @Test
    public void testRegister() throws Exception {
        val registered = new User(99,"username", "password",
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());
        given(userService.create(isA(User.class))).willReturn(registered);

        mockMvc.perform(post(PROFILE_API + "/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(new User(registered.getUsername(), registered.getPassword(), null, null))))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(registered.getUsername())));
    }

    /*@Test
    public void testUpdateProfile() throws Exception {
        given(userDetailsService.loadUserByUsername(isA(String.class))).willReturn(USER_ENC);
        super.authorize(USER);

        given(userService.get(anyInt())).willReturn(USER_ENC);

        mockMvc.perform(get(PROFILE_API)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.username", is(USER.getUsername())))
            .andExpect(jsonPath("$.password").doesNotExist());

    }*/

}
