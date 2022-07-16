CREATE TABLE IF NOT EXISTS accounts
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    login    TEXT,
    password VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS items
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN,
    account_id  INT REFERENCES accounts (id)
);

CREATE TABLE IF NOT EXISTS categories
(
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO categories(name) VALUES ('Дом');
INSERT INTO categories(name) VALUES ('Работа');
INSERT INTO categories(name) VALUES ('Магазин');
INSERT INTO categories(name) VALUES ('Медицина');
INSERT INTO categories(name) VALUES ('Машина');
INSERT INTO categories(name) VALUES ('Дача');




