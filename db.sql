create database order_db;
use order_db;

create table if not exists order_acme(
	id integer auto_increment primary key,
    address varchar(255) not null,
    data date not null,
    status varchar(255) not null
);

create table if not exists order_rel_order_item_acme(
    id_order  integer not null,
    id_order_item integer not null,
    primary key (id_order, id_order_item),
    foreign key (id_order) references order_acme(id),
    foreign key (id_order_item) references order_item_acme(id)
);

create table if not exists order_item_acme(
	id integer auto_increment primary key,
    descricao varchar(255) not null,
    preco decimal(10,2) not null,
    quantidade integer not null
);

insert into order_acme (address, data, status) values ('Araraquara', '2018-12-17' , 'AGUARDANDO');
insert into order_acme (address, data, status) values ('Araraquara', '2018-12-16' , 'AGUARDANDO');

insert into order_item_acme (descricao, preco, quantidade) values ('coca-cola', 3.50, 12);
insert into order_item_acme (descricao, preco, quantidade) values ('fanta', 2.50, 23);
insert into order_item_acme (descricao, preco, quantidade) values ('pao', 0.50, 3);

insert into order_rel_order_item_acme (id_order, id_order_item) values (1,1);
insert into order_rel_order_item_acme (id_order, id_order_item) values (1,3);
insert into order_rel_order_item_acme (id_order, id_order_item) values (2,2);

select * from order_acme;
select * from order_item_acme;
select * from order_rel_order_item_acme;

commit;