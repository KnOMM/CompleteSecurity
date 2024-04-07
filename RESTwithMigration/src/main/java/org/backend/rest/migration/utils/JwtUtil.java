package org.backend.rest.migration.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.custom.secret}")
    private String secret_key;
}
