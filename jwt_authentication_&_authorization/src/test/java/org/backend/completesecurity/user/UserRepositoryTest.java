package org.backend.completesecurity.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.backend.completesecurity.entity.UserInfo;
import org.backend.completesecurity.entity.UserRole;
import org.backend.completesecurity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired private UserRepository repo;

    @Test
    public void testCreatUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("password");

        UserInfo testUser = new UserInfo();
        testUser.setUsername("test");
        testUser.setPassword(password);
        repo.save(testUser);
    }
//
//    @Test
//    public void testAssignRoleToUser() {
//
//        UserInfo user = repo.findById(userId).get();
//        user.addRole(new UserRole(roleId2));
//
//        UserInfo updatedUser = repo.save(user);
//        assertThat(updatedUser.getRoles()).hasSize(1);
//
//    }
}
