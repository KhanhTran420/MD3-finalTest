create database productmanager;
use productmanager;

create table category(
    id int auto_increment not null  primary key ,
    name varchar(100)
);

create table product(
    id int auto_increment not null primary key ,
    name varchar(100),
    price int not null ,
    quantity int not null ,
    color varchar(50),
    description varchar(100),
    id_category int,
    foreign key (id_category) references category(id)
);

insert into category(name) values ('phone');
insert into product(name, price, quantity, color, description, id_category) VALUES ('IPhone11',1500000,2,'black','Con hang',1);

select product.id as id,
       product.name as name,
       product.price as price,
       product.quantity as quantity,
       product.color as color,
       product.description as description,
       c.id as category_id,
       c.name as categoryname
from  product join productmanager.category c on c.id = product.id_category;

select * from product join productmanager.category c on c.id = product.id_category where product.id = 1;
delete from product where id = 1;

update product set name = ?,description = ? where id = ?;

select * from category