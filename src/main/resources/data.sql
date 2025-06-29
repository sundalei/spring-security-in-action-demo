INSERT INTO authorities (username, authority)
VALUES ('john', 'write'),
       ('jack', 'read');
INSERT INTO users (username, password, enabled)
VALUES ('john', '12345', 1),
       ('jack', '23456', 1);