package org.backend.oauth2withjwt.contoller;

import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.dto.LoginResponseDTO;
import org.backend.oauth2withjwt.dto.RegistrationDTO;
import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.error.CustomUserAlreadyExistsError;
import org.backend.oauth2withjwt.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*") // not best practice
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> registerUser(@RequestBody RegistrationDTO body, UriComponentsBuilder uriComponentsBuilder, Principal principal) {
        ApplicationUser newApplicationUser = new ApplicationUser(body.getUsername(), body.getPassword());
        ApplicationUser savedApplicationUser = authenticationService.registerUser(body.getUsername(), body.getPassword());
//        if (savedApplicationUser != null) {
//
//        }
//        URI locationOfNew
//        t

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody RegistrationDTO body) {
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getUsername(), body.getPassword());
        System.out.println(loginResponseDTO);
        if (loginResponseDTO == null) throw new CustomUserAlreadyExistsError();
//        if (loginResponseDTO == null) throw new RuntimeException();
        System.out.println(loginResponseDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

}
