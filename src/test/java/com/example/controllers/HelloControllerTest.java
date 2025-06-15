package com.example.controllers;

import com.example.config.UserManagementConfig;
import com.example.config.WebAuthorizationConfig;
import com.example.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ HelloController.class, AuthController.class})
@Import({ WebAuthorizationConfig.class, UserManagementConfig.class, TokenService.class })
public class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void tokenWhenAnonymousThenStatusIsUnauthorized() throws Exception {
        this.mvc.perform(post("/token")).andExpect(status().isUnauthorized());
    }

    @Test
    void tokenWithBasicThenGetToken() throws Exception {
        MvcResult result = this.mvc.perform(
                post("/token")
                        .with(httpBasic("john", "12345")))
                .andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    void helloWhenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/hello")).andExpect(status().isUnauthorized());
    }

    @Test
    public void helloWithBasicStatusIsUnauthorized() throws Exception {
        this.mvc.perform(get("/hello")
                .with(httpBasic("john", "12345")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void rootWithMockUserStatusIsOK() throws Exception {
        this.mvc.perform(get("/hello")).andExpect(status().isOk());
    }
}
