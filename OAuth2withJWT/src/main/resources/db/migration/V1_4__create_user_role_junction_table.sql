CREATE TABLE user_role_junction
(
    user_id BINARY(16),
    role_id BINARY(16),
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
)