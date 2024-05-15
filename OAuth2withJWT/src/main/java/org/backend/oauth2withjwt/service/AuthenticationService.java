package org.backend.oauth2withjwt.service;

import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.entity.Role;
import org.backend.oauth2withjwt.repository.RoleRepository;
import org.backend.oauth2withjwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    public ApplicationUser registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("ROLE_USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        if (userRepository.findByUsername(username).isPresent()) LOGGER.error("User already exists");
        else return userRepository.save(new ApplicationUser(null, username, encodedPassword, authorities));
        return null;
    }
}
