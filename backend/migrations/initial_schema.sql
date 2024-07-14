--liquibase formatted sql

--changeset dmitriykosolobov:1
--comment: Create User table
create table user_account (
    user_id bigserial primary key,
    username text not null unique,
    email text not null unique,
    password text not null
);

--changeset dmitriykosolobov:2
--comment: Create Plant table
create table plant (
    plant_id bigserial primary key,
    name text not null unique,
    info text not null,
    temperature int not null,
    humidity int not null,
    light int not null
);

--changeset dmitriykosolobov:3
--comment: Create Device table
create table device (
    device_id bigserial primary key,
    user_id bigint not null references user_account (user_id) on delete cascade,
    plant_id bigint not null references plant (plant_id),
    battery numeric not null
);


--changeset dmitriykosolobov:4
--comment: Create Measure table
create table measure (
    measure_id bigserial primary key,
    check_time timestamp with time zone default now() not null,
    device_id bigint not null references device (device_id) on delete cascade,
    temperature int not null,
    humidity int not null,
    light int not null
);

