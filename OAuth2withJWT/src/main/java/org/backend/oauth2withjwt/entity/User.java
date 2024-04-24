package org.backend.oauth2withjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String userName;
    private String password;

    public User( String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
