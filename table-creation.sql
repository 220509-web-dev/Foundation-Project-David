create schema social_app;

set search_path to social_app;

create table app_users (
    id  int generated always as identity primary key,
    email varchar unique not null,
    age int not null,
    username varchar unique not null,
    password varchar not null
);