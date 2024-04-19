package org.example.jwt_authentication_and_authorization_with_tests;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
//@WebMvcTest
@SpringBootTest
class JwtAuthenticationAndAuthorizationWithTestsApplicationTests {
    private static final String USER_NAME = "test";
    private static final String ADMIN_NAME = "admin@gmail.com";
    private static final String PASSWORD = "test";

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(username = "testerUsername", roles = {"ADMIN"})
    public void testAdminEndpointAuthorized() throws Exception {
        mvc.perform(get("/api/v1/test/mod"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testerUsername", roles = {"USER"})
    public void testAdminEndpointUnauthorized() throws Exception {
        mvc.perform(get("/api/v1/test/mod")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_NAME)
    public void testUserEndpoint() throws Exception {
        mvc.perform(get("/api/v1/test/user"))
                .andExpect(status().isOk());
    }


}
