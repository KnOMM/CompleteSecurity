package org.backend.rest.migration.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationReq {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
