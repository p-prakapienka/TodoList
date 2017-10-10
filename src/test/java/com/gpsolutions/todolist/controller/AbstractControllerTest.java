package com.gpsolutions.todolist.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpsolutions.todolist.model.User;
import com.gpsolutions.todolist.service.UserService;
import lombok.val;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    @Qualifier("userDetailsService")
    protected UserDetailsService userDetailsService;

    protected String accessToken;

    protected void authorize(User user) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", user.getUsername());
        params.add("password", user.getPassword());
        val response = mockMvc
            .perform(post("/oauth/token")
                .with(httpBasic("clientId", "secret"))
                .params(params))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        accessToken = new JacksonJsonParser().parseMap(response).get("access_token").toString();
    }

    protected <T> String toJson(T entity) throws Exception {
        return objectMapper.writeValueAsString(entity);
    }

}
