package org.backend.completesecurity.repository;

import jakarta.transaction.Transactional;
import org.backend.completesecurity.entity.RefreshToken;
import org.backend.completesecurity.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserInfo(UserInfo userInfo);

    @Transactional
    void deleteByUserInfo(UserInfo userInfo);
}
