package org.example.jwt_authentication_and_authorization_with_tests.repository;

import org.example.jwt_authentication_and_authorization_with_tests.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{username:'?0'}")
    User findUserByUsername(String username);
}
