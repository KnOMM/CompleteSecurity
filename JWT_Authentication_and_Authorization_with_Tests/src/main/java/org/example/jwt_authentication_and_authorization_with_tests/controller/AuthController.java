package org.example.jwt_authentication_and_authorization_with_tests.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.example.jwt_authentication_and_authorization_with_tests.dto.request.LoginRequest;
import org.example.jwt_authentication_and_authorization_with_tests.dto.request.SignupRequest;
import org.example.jwt_authentication_and_authorization_with_tests.dto.response.JwtResponse;
import org.example.jwt_authentication_and_authorization_with_tests.dto.response.MessageResponse;
import org.example.jwt_authentication_and_authorization_with_tests.entity.ERole;
import org.example.jwt_authentication_and_authorization_with_tests.entity.Role;
import org.example.jwt_authentication_and_authorization_with_tests.entity.User;
import org.example.jwt_authentication_and_authorization_with_tests.repository.RoleRepository;
import org.example.jwt_authentication_and_authorization_with_tests.repository.UserRepository;
import org.example.jwt_authentication_and_authorization_with_tests.service.UserDetailsImpl;
import org.example.jwt_authentication_and_authorization_with_tests.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));


        Set<Role> roles = new HashSet<>();


        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    @PostConstruct
//    public void initUsers() {
//        roleRepository.save(new Role(ERole.ROLE_USER));
//        roleRepository.save(new Role(ERole.ROLE_ADMIN));
//        roleRepository.save(new Role(ERole.ROLE_MODERATOR));
//
//        User admin = new User("admin",
//                "admin@admin.com",
//                encoder.encode("admin"));
//
//        Set<Role> roles = new HashSet<>();
//        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        roles.add(userRole);
//
//        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        roles.add(adminRole);
//
//        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        roles.add(modRole);
//        admin.setRoles(roles);
//        userRepository.save(admin);
//
//        // create moderator user
//        User mod = new User("mod",
//                "mod@mod.com",
//                encoder.encode("mod"));
//
//        Set<Role> roles2 = new HashSet<>();
//        roles2.add(userRole);
//        roles2.add(modRole);
//        mod.setRoles(roles2);
//        userRepository.save(mod);
//    }

}
