package org.backend.oauth2withjwt;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.backend.oauth2withjwt.dto.RegistrationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.Objects;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationUserAuthTests {

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void shouldLoginValidUser() {
        RegistrationDTO loginUser = new RegistrationDTO("user2", "password");
        ResponseEntity<String> response = restTemplate
                .postForEntity("/auth/login", loginUser, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String username = documentContext.read("$.user.username");
        String password = documentContext.read("$.user.password");

        assertThat(username).isEqualTo("user2");
        System.out.println(password);
        System.out.println(passwordEncoder.encode("password"));
    }

    @Test
    void shouldDenyBadUser() {
        RegistrationDTO loginUser = new RegistrationDTO("user2", "incorrect-password");
        ResponseEntity<String> responseUsername = restTemplate
                .postForEntity("/auth/login", loginUser, String.class);
        assertThat(responseUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseUsername.getBody()).isEqualTo("Error from controller advice");

        RegistrationDTO badLoginUser = new RegistrationDTO("bad-user", "password");
        ResponseEntity<String> responsePassword = restTemplate
                .postForEntity("/auth/login", badLoginUser, String.class);
        assertThat(responsePassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responsePassword.getBody()).isEqualTo("Error from controller advice");

    }


}
