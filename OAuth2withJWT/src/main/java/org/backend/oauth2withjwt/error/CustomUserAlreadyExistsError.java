package org.backend.oauth2withjwt.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="User with such username already exists")  // 401
public class CustomUserAlreadyExistsError extends RuntimeException {
}
