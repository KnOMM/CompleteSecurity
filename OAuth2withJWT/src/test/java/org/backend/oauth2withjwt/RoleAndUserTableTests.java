package org.backend.oauth2withjwt;

import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.entity.Role;
import org.backend.oauth2withjwt.repository.RoleRepository;
import org.backend.oauth2withjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;



import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleAndUserTableTests {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUserRolesIfNotExist() {
        if ( roleRepository.findByAuthority("ROLE_ADMIN").isEmpty()) {
            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");
            roleRepository.save(admin);
        }

        if (roleRepository.findByAuthority("ROLE_MODERATOR").isEmpty()) {
            Role moderator = new Role();
            moderator.setAuthority("ROLE_MODERATOR");
            roleRepository.save(moderator);
        }

        if (roleRepository.findByAuthority("ROLE_USER").isEmpty()) {
            Role user = new Role();
            user.setAuthority("ROLE_USER");
            roleRepository.save(user);
        }

        System.out.println(roleRepository.findAll());

        List<Role> roles = roleRepository.findAll();
        Role[] arrayRoles = roles.toArray(new Role[0]);
        assertThat(roles).containsExactlyInAnyOrder( arrayRoles);

        long count = roleRepository.count();
        assertEquals(3, count);
    }


    @Test
    public void testUserCreationIfNotExist() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsername("admin").isEmpty()) {
            ApplicationUser admin = new ApplicationUser( );
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin);
        }

        assertEquals(userRepository.findAll().size(), 1);
        Optional<ApplicationUser> admin = userRepository.findByUsername("admin");

        assertThat(admin.orElseThrow().getUsername()).isEqualTo("admin");
    }

}
