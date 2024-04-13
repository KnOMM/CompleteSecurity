# Useful commands

to run from the command line
```commandline
mvn spring-boot:run
```

users table creation
```postgresql
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
```

insert test user
```postgresql
INSERT INTO users(first_name, last_name, email, password, profile_photo_url, country, occupation, gender, role, date_of_birth)
VALUES ('James','Smith','james19@example.com','james123','','Australia','Doctor','Male','USER',timestamp '1987-01-10');
```

command line option
```commandline
sudo -u postgres psql
```