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