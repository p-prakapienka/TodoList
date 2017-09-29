package com.gpsolutions.todolist.controller;

import static com.gpsolutions.todolist.data.TestData.USER;
import static com.gpsolutions.todolist.data.TestData.USER_ENC;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gpsolutions.todolist.service.TodoService;
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

        mockMvc.perform(get("/api/admin/todo/user/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + accessToken))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

}
