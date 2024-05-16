create table roles
(
    role_id   BINARY(16) primary key,
    authority ENUM ('USER', 'MODERATOR', 'ADMIN') not null
);

DELIMITER //
CREATE TRIGGER before_insert_roles
    BEFORE INSERT ON roles
    FOR EACH ROW
    IF new.role_id IS NULL
    THEN
        SET new.role_id = UUID_TO_BIN(UUID());
    END IF; //
DELIMITER ;


CREATE VIEW roles_view AS
SELECT
    role_id,
    authority,
    BIN_TO_UUID(role_id) AS role_uuid
FROM
    roles;