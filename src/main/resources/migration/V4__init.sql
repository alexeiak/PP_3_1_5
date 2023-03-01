INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (age, email, first_name, last_name, password)
VALUES
    -- password = admin
    (40, 'admin@mail.ru', 'Admin', 'Adminf', '$2a$12$Z67/1v1QcK0vyttHb8mlYOFfnJzkViTsTekzzGXay9PHcs/h.MpO.'),
    -- password = user
    (30, 'user@mail.ru', 'User', 'Userf', '$2a$12$jue8UkxNx.eAIouDRqxWre4D4s9VSrQYwf9y2.bVO.o2YXudl4S4K'),

    (20, 'user2@mail.ru', 'User2', 'Userf2', '$2a$12$jue8UkxNx.eAIouDRqxWre4D4s9VSrQYwf9y2.bVO.o2YXudl4S4K');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), (1, 2),
       (2, 2),
       (3, 2);