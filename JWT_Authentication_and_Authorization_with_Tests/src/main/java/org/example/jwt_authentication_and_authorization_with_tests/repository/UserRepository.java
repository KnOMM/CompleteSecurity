package org.example.jwt_authentication_and_authorization_with_tests.repository;

import org.example.jwt_authentication_and_authorization_with_tests.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
