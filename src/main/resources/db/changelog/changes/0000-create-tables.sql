--liquibase formatted sql

--changeset rubel:0000_1 splitStatements:true endDelimiter:;
create table if not exists users
(
    id bigint not null
        constraint users_pkey
            primary key,
    email varchar(255),
    name varchar(255),
    username varchar(255)
);

create table if not exists tasks
(
    id bigint not null
        constraint tasks_pkey
            primary key,
    description varchar(255),
    title varchar(255),
    type varchar(255),
    user_id bigint
        constraint fk6s1ob9k4ihi75xbxe2w0ylsdh
            references users
            on delete cascade
);

--rollback DROP TABLE IF EXISTS tasks;
--rollback DROP TABLE IF EXISTS users;


