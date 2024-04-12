package org.backend.rest.migration.controller;

import lombok.RequiredArgsConstructor;
import org.backend.rest.migration.model.User;
import org.backend.rest.migration.model.request.LoginReq;
import org.backend.rest.migration.model.response.ErrorRes;
import org.backend.rest.migration.model.response.LoginRes;
import org.backend.rest.migration.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/rest/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReq loginReq) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
            );
            String email = authentication.getName();
            User user = new User(email, "");
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email, token);
            return ResponseEntity.ok(loginRes);
        } catch (BadCredentialsException e) {
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        } catch (Exception e) {

            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
