package org.backend.completesecurity.repository;

import org.backend.completesecurity.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<UserRole, Long> {
}
