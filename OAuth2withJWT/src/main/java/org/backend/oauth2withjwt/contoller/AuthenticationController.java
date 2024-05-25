package org.backend.oauth2withjwt.contoller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.dto.LoginResponseDTO;
import org.backend.oauth2withjwt.dto.RegistrationDTO;
import org.backend.oauth2withjwt.entity.ApplicationUser;
import org.backend.oauth2withjwt.error.CustomUserAlreadyExistsStatus;
import org.backend.oauth2withjwt.error.ErrorInfo;
import org.backend.oauth2withjwt.error.MyBadDataException;
import org.backend.oauth2withjwt.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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


        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody RegistrationDTO body) throws MyBadDataException {
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getUsername(), body.getPassword());
//        if (loginResponseDTO == null) throw new MyBadDataException();
        if (loginResponseDTO == null) throw new IllegalArgumentException();
//        if (loginResponseDTO == null) throw new CustomUserAlreadyExistsStatus();
//        System.out.println(loginResponseDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MyBadDataException.class)
    @ResponseBody ErrorInfo
    handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

}
