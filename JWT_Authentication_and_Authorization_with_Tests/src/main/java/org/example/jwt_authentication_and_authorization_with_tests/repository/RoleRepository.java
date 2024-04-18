package org.example.jwt_authentication_and_authorization_with_tests.repository;

import org.example.jwt_authentication_and_authorization_with_tests.entity.ERole;
import org.example.jwt_authentication_and_authorization_with_tests.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
