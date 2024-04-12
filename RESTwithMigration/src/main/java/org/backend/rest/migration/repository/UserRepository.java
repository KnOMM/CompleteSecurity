package org.backend.rest.migration.repository;

import org.backend.rest.migration.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User findUserByEmail(String email) {
        User user = new User(email, "password");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        return user;
    }
}
