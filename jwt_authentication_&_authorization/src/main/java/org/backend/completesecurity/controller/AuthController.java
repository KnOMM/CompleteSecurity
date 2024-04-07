package org.backend.completesecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.backend.completesecurity.config.JwtAuthFilter;
import org.backend.completesecurity.dto.AuthRequestDTO;
import org.backend.completesecurity.dto.JwtResponseDTO;
import org.backend.completesecurity.dto.RefreshTokenRequestDTO;
import org.backend.completesecurity.entity.RefreshToken;
import org.backend.completesecurity.entity.UserInfo;
import org.backend.completesecurity.repository.UserRepository;
import org.backend.completesecurity.service.InMemoryTokenBlacklist;
import org.backend.completesecurity.service.JwtService;
import org.backend.completesecurity.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private final InMemoryTokenBlacklist tokenBlacklist;

    @Autowired
    private final UserRepository userRepository;

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());

            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request!!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is no in DB"));
    }

    @PostMapping("/logout")
    public  ResponseEntity<String> logout(HttpServletRequest request, Authentication authentication) {
        String token = JwtAuthFilter.extractTokenFromRequest(request);
        tokenBlacklist.addToBlacklist(token);
        if (authentication != null){
            UserInfo userInfo = userRepository.findByUsername(
                    ((UserInfo)authentication.getPrincipal())
                            .getUsername()
            );
            refreshTokenService.deleteRefreshToken(userInfo);
        }

        return ResponseEntity.ok("Logged out successfully!!!");
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/ping")
    public String test(Authentication authentication) {
        try {
//            return authentication.getAuthorities().toString();
            return "Welcome";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
