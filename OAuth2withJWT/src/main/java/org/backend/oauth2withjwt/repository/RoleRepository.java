package org.backend.oauth2withjwt.repository;

import org.backend.oauth2withjwt.entity.ERole;
import org.backend.oauth2withjwt.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
