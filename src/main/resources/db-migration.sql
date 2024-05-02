DROP TABLE IF EXISTS post_album;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS album CASCADE;

CREATE TABLE users
(
    id       bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    username varchar(64) NOT NULL
);

CREATE TABLE post
(
    id        bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    content   varchar(4096) NOT NULL,
    author_id bigint        NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE album
(
    id          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    title       varchar(256) NOT NULL,
    description varchar(4096),
    author_id   bigint       NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX album_author_title_idx ON album (author_id, title);

CREATE TABLE post_album
(
  --  id       bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    post_id  bigint NOT NULL,
    album_id bigint NOT NULL,

    CONSTRAINT pk_post_album PRIMARY KEY (post_id, album_id),
    CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    CONSTRAINT fk_album_id FOREIGN KEY (album_id) REFERENCES album (id) ON DELETE CASCADE
);

INSERT INTO users (username)
VALUES ('Harry Potter'),
       ('Hermione Granger'),
       ('Ronald Weasley'),
       ('Draco Malfoy'),
       ('Neville Longbottom');

INSERT INTO post (content, author_id)
VALUES ('Content1', 1),
       ('Content2', 1),
       ('Content3', 2),
       ('Content4', 2),
       ('Content5', 3),
       ('Content6', 3),
       ('Content7', 4),
       ('Content8', 4);

INSERT INTO album (title, description, author_id)
VALUES ('Title1', 'Description1', 1),
       ('Title2', 'Description2', 1),
       ('Title3', null, 2),
       ('Title4', 'Description4', 2),
       ('Title5', 'Description5', 3),
       ('Title6', 'Description6', 4);