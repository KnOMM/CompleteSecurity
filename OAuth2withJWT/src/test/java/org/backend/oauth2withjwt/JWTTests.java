package org.backend.oauth2withjwt;

import org.backend.oauth2withjwt.contoller.AuthenticationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
public class JWTTests {
    @Autowired
    private MockMvc mockMvc;


//    @BeforeEach
//    void setup() {
//        this.mockMvc = MockMvcBuilders.build();
//    }

    @Test
    public void testInvalidEntrypoint() throws Exception {
        mockMvc.perform(get("/user").with(user("notCorrectEntry"))) .andExpect(status().isNotFound());

    }

    @Test
    public void testWithAuthorizedUser() throws Exception {
        mockMvc.perform(get("/admin/")
                .with(user("admin")
                        .password("pass")
                        .roles("USER","ADMIN")))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void testWithUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/admin/")
                .with(user("user")
                        .password("pass")
                        .roles("USER")))
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    public void testWithAnonymousUser() throws Exception {
        mockMvc.perform(get("/user/").with(anonymous())).andExpect(status().isUnauthorized());
    }

    @Test
    public void testWithoutJWT() throws Exception {
        mockMvc.perform(get("/user/")).andExpect(status().isUnauthorized());
    }

    @Test
    public void testWithImproperJWT() throws Exception {
        mockMvc.perform(get("/user/").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_MANAGER")))).andExpect(status().isForbidden());
    }

    @Test
    void testLoginEndpointWithValidUser() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
//                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                                .content("{\n" +
                                        "    \"username\":\"admin\",\n" +
                                        "    \"password\":\"admin\"\n" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    @Test
    void testLoginEndpointWithBadUser() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
//                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                                .content("{\n" +
                                        "    \"username\":\"nouser\",\n" +
                                        "    \"password\":\"password\"\n" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();

    }
}
