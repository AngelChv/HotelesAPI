create DATABASE if not exists hoteles_api;
USE hoteles_api;
create table if not exists hoteles
(
    id          int auto_increment
        primary key,
    piscina     bit          not null,
    categoria   varchar(255) not null,
    descripcion varchar(255) null,
    localidad   varchar(255) not null,
    nombre      varchar(255) not null
);

create table if not exists habitaciones
(
    capacidad        int    not null,
    desayuno         bit    not null,
    hotel_id         int    null,
    id               int auto_increment
        primary key,
    ocupada          bit    not null,
    precio_por_noche double not null,
    constraint FKrnrx4788kbajol7hnp3v8oj2h
        foreign key (hotel_id) references hoteles (id)
);

create table if not exists users
(
    id       int auto_increment
        primary key,
    password varchar(255) not null,
    username varchar(255) not null,
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username)
);


insert into users(username, password)
values ('juan', 'ed08c290d7e22f7bb324b15cbadce35b0b348564fd2d5f95752388d86d71bcca');