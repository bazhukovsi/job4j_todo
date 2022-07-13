CREATE TABLE IF NOT EXISTS accounts
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    login    TEXT,
    password VARCHAR(20)
);