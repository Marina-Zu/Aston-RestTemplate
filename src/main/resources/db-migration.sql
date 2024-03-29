DROP TABLE IF EXISTS post_album;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS album CASCADE;

CREATE TABLE users
(
    id       bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    username varchar(64) UNIQUE NOT NULL
);

CREATE TABLE post
(
    id      bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    content varchar(4096) NOT NULL

);

CREATE TABLE album
(
    id          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    title       varchar(256) NOT NULL,
    description varchar(4096) NOT NULL
);

INSERT INTO users (username)
VALUES
    ('Harry Potter'),
    ('Hermione Granger'),
    ('Ronald Weasley'),
    ('Draco Malfoy'),
    ('Neville Longbottom');