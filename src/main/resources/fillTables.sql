INSERT INTO users (first_name, last_name, age, password, email)
VALUES ('admin', 'admlastname', 30, '$2a$12$7S3F7NAWUDEP/JTVBIIVKOqNg09pHL.ryBFuvdjv0W0hPp4CV4smi', 'admin@mail.ru'),
       ('user', 'userlastname', 20, '$2a$12$BDutbA/1NRimpaRpvHc8KepZZCSeGxo6QzYbmm3.N7d66oiyl/xdm', 'user@mail.ru'),
       ('user02', 'userlastname', 20, '$2a$12$BDutbA/1NRimpaRpvHc8KepZZCSeGxo6QzYbmm3.N7d66oiyl/xdm', 'user02@mail.ru');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), (1, 2),
       (2, 2),
       (3, 2);