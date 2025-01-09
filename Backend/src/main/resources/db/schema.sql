-- Task Table
create table if not exists task
(
    id          serial primary key,
    title       varchar(255) not null,
    description text,
    priority    integer   default 0,
    due_date    date,
    completed   boolean   default false,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

-- User Table
create table if not exists users
(
    id         serial primary key,
    username   varchar(255)        not null,
    email      varchar(255) unique not null,
    password   varchar(255)        not null,
    roles      VARCHAR(50)         NOT NULL,
    created_at timestamp default now(),
    updated_at timestamp default now()
);