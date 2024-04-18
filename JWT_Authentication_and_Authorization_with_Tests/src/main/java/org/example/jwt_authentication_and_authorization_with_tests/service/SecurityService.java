package org.example.jwt_authentication_and_authorization_with_tests.service;

public interface SecurityService {
    boolean login(String username, String password);
}
