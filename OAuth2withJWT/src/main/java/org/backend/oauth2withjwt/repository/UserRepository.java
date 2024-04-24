package org.backend.oauth2withjwt.repository;

import java.util.Optional;
import org.backend.oauth2withjwt.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUserName(String username);
    Optional<User> findByUserName(String username);
}
