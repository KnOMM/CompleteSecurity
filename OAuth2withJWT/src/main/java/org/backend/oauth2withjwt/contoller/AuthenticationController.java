package org.backend.oauth2withjwt.contoller;

import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.dto.LoginResponseDTO;
import org.backend.oauth2withjwt.dto.RegistrationDTO;
import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*") // not best practice
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
