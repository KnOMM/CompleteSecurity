package org.backend.oauth2withjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.oauth2withjwt.entity.ApplicationUser;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {

    private ApplicationUser user;
    private String jwt;
}
