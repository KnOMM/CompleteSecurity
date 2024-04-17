CREATE TABLE IF NOT EXISTS stories
(
    id         serial PRIMARY KEY,
    title      TEXT,
    body      TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);