CREATE TABLE IF NOT EXISTS items
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN
);


