package org.example.jwt_authentication_and_authorization_with_tests;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.jwt_authentication_and_authorization_with_tests.entity.ERole;
import org.example.jwt_authentication_and_authorization_with_tests.entity.Role;
import org.example.jwt_authentication_and_authorization_with_tests.entity.User;
import org.example.jwt_authentication_and_authorization_with_tests.repository.RoleRepository;
import org.example.jwt_authentication_and_authorization_with_tests.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthenticationAndAuthorizationWithTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthenticationAndAuthorizationWithTestsApplication.class, args);
    }
}
