package org.example.jwt_authentication_and_authorization_with_tests;


import org.example.jwt_authentication_and_authorization_with_tests.config.SecurityConfig;
import org.example.jwt_authentication_and_authorization_with_tests.controller.AuthController;
import org.example.jwt_authentication_and_authorization_with_tests.repository.UserRepository;
import org.example.jwt_authentication_and_authorization_with_tests.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultHandlers.exportTestSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc
//@WebMvcTest(AuthController.class)
@SpringBootTest
class JwtAuthenticationAndAuthorizationWithTestsApplicationTests {
    private static final String USER_NAME = "test";
    private static final String ADMIN_NAME = "admin@gmail.com";
    private static final String PASSWORD = "test";

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testAdminEndpointAuthorized() throws Exception {
        mvc.perform(get("/api/v1/test/mod"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"}) // USER role is by default - can be omitted
    public void testUserEndpointAuthorized() throws Exception {
        mvc.perform(get("/api/v1/test/user")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testModEndpointUnauthorized() throws Exception {
        mvc.perform(get("/api/v1/test/mod")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testAdminEndpointUnauthorized() throws Exception {
        mvc.perform(get("/api/v1/test/admin")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_NAME)
    public void testUserEndpoint() throws Exception {
        mvc.perform(get("/api/v1/test/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testPublicWithAnonymousUser() throws Exception {
        mvc.perform(get("/api/v1/test/all")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUserEndpointWithAnonymousUser() throws Exception {
        mvc.perform(get("/api/v1/test/user")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    public void testUserThatExistsCreation() throws Exception {
        mvc.perform(post("/api/v1/auth/signup")
                        .content("{\n" +
                                "    \"username\":\"test\",\n" +
                                "    \"password\":\"password\",\n" +
                                "    \"email\":\"test@test.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(exportTestSecurityContext())
                .andExpect(status().isBadRequest());
    }
}
