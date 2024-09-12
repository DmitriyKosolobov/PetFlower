--liquibase formatted sql

--changeset dmitriykosolobov:1
--comment: Create User table
create table user_account (
    user_id bigserial primary key,
    username varchar(20) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null
);

--changeset dmitriykosolobov:2
--comment: Create UserInfo table
create table user_info (
    user_id bigserial primary key references user_account (user_id) on delete cascade,
    info text null
);

--changeset dmitriykosolobov:3
--comment: Create Plant table
create table plant (
    plant_id bigserial primary key,
    name varchar(255) not null unique,
   	max_light_lux int not null,
   	min_light_lux int not null,
   	max_temp int not null,
   	min_temp int not null,
   	max_env_humid int not null,
   	min_env_humid int not null,
   	max_soil_moist int not null,
   	min_soil_moist int not null
);

--changeset dmitriykosolobov:4
--comment: Create PlantInfo table
create table plant_info (
    plant_id bigserial primary key references plant (plant_id) on delete cascade,
    info text null
);

--changeset dmitriykosolobov:5
--comment: Create Device table
create table device (
    device_id bigserial primary key,
    device_key varchar(255) not null unique,
    user_id bigint not null references user_account (user_id) on delete cascade
);

--changeset dmitriykosolobov:6
--comment: Create Measure table
create table measure (
    measure_id bigserial primary key,
    check_time timestamp with time zone default now() not null,
    device_id bigint not null references device (device_id) on delete cascade,
    temp int not null,
    env_humid int not null,
    light_lux int not null,
    soil_moist int not null,
    battery_level numeric not null
);

--changeset dmitriykosolobov:7
--comment: Create Pet table
create table pet (
    pet_id bigserial primary key,
    name varchar(50) not null,
    user_id bigint not null references user_account (user_id) on delete cascade,
    plant_id bigint null references plant (plant_id),
    device_id bigint null references device (device_id),
    unique (name, user_id)
);