package org.backend.completesecurity.repository;

import org.backend.completesecurity.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
