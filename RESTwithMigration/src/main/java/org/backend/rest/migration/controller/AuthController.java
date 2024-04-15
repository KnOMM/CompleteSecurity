package org.backend.rest.migration.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.backend.rest.migration.model.User;
import org.backend.rest.migration.model.request.LoginReq;
import org.backend.rest.migration.model.request.RegistrationReq;
import org.backend.rest.migration.model.response.ErrorRes;
import org.backend.rest.migration.model.response.LoginRes;
import org.backend.rest.migration.service.UserService;
import org.backend.rest.migration.utils.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/rest/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @ResponseBody
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
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password" + loginReq.getEmail() + loginReq.getPassword());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        } catch (Exception e) {

            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity register(HttpServletRequest request, HttpServletResponse response, @RequestBody RegistrationReq registrationReq) {
        try {
            User user = new User();
            user.setEmail(registrationReq.getEmail());
            user.setFirstName(registrationReq.getFirstName());
            user.setLastName(registrationReq.getLastName());
            user.setPassword(registrationReq.getPassword());
            user.setRole("USER");
            // encryption
            String password = registrationReq.getPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            user.setPassword(encodedPassword);

            User newUser = userService.createUser(user);
            if(newUser == null) {
                ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Error creating a new user");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            newUser.setPassword("");
            String token = jwtUtil.createToken(newUser);
            LoginRes loginRes = new LoginRes(newUser.getEmail(), token);
            return ResponseEntity.ok(loginRes);
        } catch (DataIntegrityViolationException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Such user already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
