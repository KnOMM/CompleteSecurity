package org.backend.oauth2withjwt.service;

import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.entity.Role;
import org.backend.oauth2withjwt.repository.RoleRepository;
import org.backend.oauth2withjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the User Details Service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
