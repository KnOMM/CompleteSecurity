package org.backend.oauth2withjwt.dto;

import lombok.*;
import org.backend.oauth2withjwt.entity.ApplicationUser;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDTO {

    private ApplicationUser user;
    private String jwt;
}
