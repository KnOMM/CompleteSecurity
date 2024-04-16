CREATE TABLE IF NOT EXISTS stories
(
    id         serial PRIMARY KEY,
    story      TEXT,
    created_at TIMESTAMP,
    user_id    INT,
    CONSTRAINT fk_stories
        FOREIGN KEY (user_id) REFERENCES users (id)

);