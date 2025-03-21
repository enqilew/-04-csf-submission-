-- IMPORTANT: DO NOT CHANGE THE GIVEN SCHEMA UNLESS YOU HAVE A GOOD REASON
-- IF YOU DO CHANGE IT WRITE THE JUSTIFICATION IN A COMMENT ABOVE THE CHANGE

drop database if exists restaurant;

create database restaurant;

use restaurant;

-- Error Code: 6125. Failed to add the foreign key constraint. 
-- Missing unique key for constraint 'place_orders_ibfk_1' in the referenced table 'customers'
-- add UNIQUE constraint to the username
create table customers (
  username varchar(64) not null UNIQUE,
  password varchar(128) not null
);

insert into customers(username, password) values
  ('fred', sha2('fred', 224)),
  ('barney', sha2('barney', 224)),
  ('wilma', sha2('wilma', 224)),
  ('betty', sha2('betty', 224)),
  ('pebbles', sha2('pebbles', 224));

-- TODO: Task 1.2
-- Write your task 1.2 below
CREATE TABLE place_orders (
  order_id CHAR(8) PRIMARY KEY,
  payment_id VARCHAR(128),
  order_date DATE,
  total DECIMAL(10, 2),
  username VARCHAR(64),
  FOREIGN KEY (username) REFERENCES customers(username)
);
