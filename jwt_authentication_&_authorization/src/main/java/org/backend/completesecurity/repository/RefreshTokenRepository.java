package org.backend.completesecurity.repository;

import org.backend.completesecurity.entity.RefreshToken;
import org.backend.completesecurity.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserInfo(UserInfo userInfo);
    void deleteRefreshTokenById(Long id);
}
