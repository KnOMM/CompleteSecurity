create table users
(
    user_id   BINARY(16) primary key,
    username varchar(100) not null unique,
    password varchar(128) not null
);

DELIMITER //
CREATE TRIGGER before_insert_users
    BEFORE INSERT ON users
    FOR EACH ROW
    IF new.user_id IS NULL
    THEN
        SET new.user_id = UUID_TO_BIN(UUID());
    END IF; //
DELIMITER ;


CREATE VIEW users_view AS
SELECT
    user_id,
    username,
    BIN_TO_UUID(user_id) AS role_uuid
FROM
    users;