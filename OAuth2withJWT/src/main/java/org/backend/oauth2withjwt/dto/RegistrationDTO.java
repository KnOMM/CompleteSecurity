package org.backend.oauth2withjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationDTO {
    private String username;
    private String password;
}
