create table client
(
    id   bigserial    not null primary key,
    name varchar(200) not null
);
create table address
(
    id     bigserial    not null primary key,
    street varchar(500) not null,
    client_id bigserial not null references Client (id)
);
create table phone
(
    id     bigserial   not null primary key,
    number varchar(20) not null,
    client_id bigserial not null references Client (id)
);