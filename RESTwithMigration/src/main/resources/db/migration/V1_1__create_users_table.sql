CREATE TABLE IF NOT EXISTS users
(
    id                serial PRIMARY KEY,
    country           VARCHAR(255),
    date_of_birth     TIMESTAMP,
    email             VARCHAR(255) NOT NULL UNIQUE,
    first_name        VARCHAR(255) NOT NULL,
    gender            VARCHAR(255),
    last_name         VARCHAR(255) NOT NULL,
    occupation        VARCHAR(255),
    password          VARCHAR(255) NOT NULL,
    profile_photo_url VARCHAR(255),
    role              VARCHAR(255)
    );