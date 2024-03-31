package org.backend.completesecurity.service;

import org.backend.completesecurity.config.CustomUserDetails;
import org.backend.completesecurity.entity.UserInfo;
import org.backend.completesecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Username nod found: {}", username);
            throw new UsernameNotFoundException("could not find user " + username);
        }
        logger.info("User Authenticated Successfully!!!");
        return new CustomUserDetails(user);
    }
}
