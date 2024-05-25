package org.backend.oauth2withjwt;

import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.entity.Role;
import org.backend.oauth2withjwt.repository.RoleRepository;
import org.backend.oauth2withjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleAndUserTableTests {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {

        ApplicationUser admin = new ApplicationUser( );
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
    }

    @Test
    public void testCreateUserRolesIfNotExist() {
        if ( roleRepository.findByAuthority("ADMIN").isEmpty()) {
            Role admin = new Role();
            admin.setAuthority("ADMIN");
            roleRepository.save(admin);
        }

        if (roleRepository.findByAuthority("MODERATOR").isEmpty()) {
            Role moderator = new Role();
            moderator.setAuthority("MODERATOR");
            roleRepository.save(moderator);
        }

        if (roleRepository.findByAuthority("USER").isEmpty()) {
            Role user = new Role();
            user.setAuthority("USER");
            roleRepository.save(user);
        }

//        System.out.println(roleRepository.findAll());

        List<Role> roles = roleRepository.findAll();
        Role[] arrayRoles = roles.toArray(new Role[0]);
        assertThat(roles).containsExactlyInAnyOrder( arrayRoles);

        long count = roleRepository.count();
        assertEquals(3, count);
    }


    @Test
    public void testUserCreationIfNotExist() {

        if (userRepository.findByUsername("admin").isEmpty()) {
            ApplicationUser admin = new ApplicationUser( );
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("sarah1").isEmpty()) {
            ApplicationUser admin = new ApplicationUser( );
            admin.setUsername("sarah1");
            admin.setPassword(passwordEncoder.encode("abc123"));
            userRepository.save(admin);
        }

        assertEquals(userRepository.findAll().size(), 2);
        Optional<ApplicationUser> admin = userRepository.findByUsername("admin");

        assertThat(admin.orElseThrow().getUsername()).isEqualTo("admin");
    }

    @Test
    @Order(16)
    public void testUserRoleMapping() {

        ApplicationUser admin = userRepository.findByUsername("admin").orElseThrow(RuntimeException::new);
        Role roleAdmin = roleRepository.findByAuthority("ADMIN").orElseThrow(RuntimeException::new);
        Set<Role> roles = new HashSet<>();
        roles.add(roleAdmin);
        admin.setAuthorities(roles);

        assertThat(userRepository.findByUsername("admin").get().getAuthorities().contains(roleAdmin));
    }

}
