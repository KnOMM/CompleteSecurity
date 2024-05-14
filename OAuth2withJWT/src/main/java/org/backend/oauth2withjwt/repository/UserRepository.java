package org.backend.oauth2withjwt.repository;

import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, UUID> {
    boolean existsByUsername(String username);
    Optional<ApplicationUser> findByUsername(String username);
}
