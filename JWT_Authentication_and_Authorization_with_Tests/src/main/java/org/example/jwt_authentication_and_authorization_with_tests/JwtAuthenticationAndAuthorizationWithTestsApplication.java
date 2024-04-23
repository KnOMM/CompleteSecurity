package org.example.jwt_authentication_and_authorization_with_tests;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthenticationAndAuthorizationWithTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthenticationAndAuthorizationWithTestsApplication.class, args);
    }
}
