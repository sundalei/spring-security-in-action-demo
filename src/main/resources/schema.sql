CREATE TABLE IF NOT EXISTS my_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    enabled INT NOT NULL);

CREATE TABLE IF NOT EXISTS my_authorities (
    id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    authority VARCHAR(45) NOT NULL);