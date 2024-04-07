package org.backend.completesecurity.service;

import jakarta.transaction.Transactional;
import org.backend.completesecurity.entity.RefreshToken;
import org.backend.completesecurity.entity.UserInfo;
import org.backend.completesecurity.repository.RefreshTokenRepository;
import org.backend.completesecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        UserInfo byUsername = userRepository.findByUsername(username);
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(byUsername)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // 10 min
                .build();
        if (refreshTokenRepository.findByUserInfo(byUsername).isPresent()) {
            refreshTokenRepository.deleteByUserInfo(byUsername);
            return refreshTokenRepository.save(refreshToken);
        }
        return refreshTokenRepository.save(refreshToken);
    }


    public void deleteRefreshToken(UserInfo userInfo) {
        refreshTokenRepository.deleteByUserInfo(userInfo);
//        System.out.println(refreshTokenRepository.findAll());
        return;
    }
//
//    public RefreshToken createRefreshToken(String username) {
//        UserInfo byUsername = userRepository.findByUsername(username);
//        RefreshToken refreshToken = RefreshToken.builder()
//                .userInfo(byUsername)
//                .token(UUID.randomUUID().toString())
//                .expiryDate(Instant.now().plusMillis(600000)) // 10 min
//                .build();
//        if (refreshTokenRepository.findByUserInfo(byUsername).isPresent()) {
//            deleteRefreshToken(byUsername);
////            refreshTokenRepository.deleteRefreshTokenById(refreshTokenRepository.findByUserInfo(byUsername).get().getId());
//            return refreshTokenRepository.save(refreshToken);
//        }
//        return refreshTokenRepository.save(refreshToken);
//    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login!!!");
        }
        return token;
    }
}
