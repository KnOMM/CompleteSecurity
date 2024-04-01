package org.backend.completesecurity.role;
import org.backend.completesecurity.entity.UserInfo;
import org.backend.completesecurity.entity.UserRole;
import org.backend.completesecurity.repository.RoleRepository;
import org.backend.completesecurity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repo;
    @Autowired
    private UserRepository userRepo;

    @Test
    public void testCreateUserRoles() {
        UserRole admin = new UserRole();
        admin.setName("Role_ADMIN");
        UserRole editor = new UserRole();
        editor.setName("Role_EDITOR");
        UserRole customer = new UserRole();
        customer.setName("Role_CUSTOMER");

        repo.saveAll(List.of(admin, editor, customer));

        long count = repo.count();
        assertEquals(3, count);
    }

    @Test
    public void testAssignUserToRole() {


        UserInfo user = userRepo.findByUsername("test");
        user.addRole(repo.findById(1L).orElse(null));

        userRepo.save(user);
    }
}
