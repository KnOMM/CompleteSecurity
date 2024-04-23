package org.example.jwt_authentication_and_authorization_with_tests.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    @Id
    private String id;
    private ERole name;


    public Role(ERole name) {
        this.name = name;
    }
}
