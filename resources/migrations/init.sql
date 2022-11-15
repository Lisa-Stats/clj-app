-- drop tables
drop table if exists random;

-- create schema

create table random (
   id serial primary key,
   name text unique not null,
   number int not null
);
