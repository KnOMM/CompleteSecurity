package org.backend.rest.migration.service;

import lombok.RequiredArgsConstructor;
import org.backend.rest.migration.model.User;
import org.backend.rest.migration.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User createUser(User user) {
        User newUser = userRepository.save(user);
        userRepository.flush();
        return newUser;
    }
}
