insert into task (title, description, priority, due_date, completed)
values ('Task 1', 'Description for task 1', 1, '2025-01-10', false),
       ('Task 2', 'Description for task 2', 2, '2025-01-10', false);

insert into users (username, email, password, roles)
values ('user', 'user@test.com','user', false),
       ('admin', 'admin@admin.com', 'admin',true)
ON CONFLICT (email) DO NOTHING;