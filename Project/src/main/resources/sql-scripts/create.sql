drop database "CABD Project";
drop table customer_order;
drop table customer;
drop table item;
drop table item_history;

create database "CABD Project";

create table if not exists customer (id serial NOT NULL PRIMARY KEY, name text, address text);
create table if not exists item (id serial NOT NULL PRIMARY KEY, price numeric, name text, description text);
create table if not exists item_history (id serial NOT NULL PRIMARY KEY, item_id numeric, name text, description text, price numeric, t_start timestamp, t_end timestamp);
create table if not exists  customer_order (order_id integer NOT NULL , item_id integer references item, customer_id integer references customer, comments text, quantity numeric);
