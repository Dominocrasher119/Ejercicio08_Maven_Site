DROP DATABASE IF EXISTS bd_hotel_mvn;
CREATE DATABASE bd_hotel_mvn;

USE bd_hotel_mvn;

CREATE TABLE Hoteles (
id INT auto_increment primary key,
nombre varchar(100) NOT NULL,
num_plantas INT NOT NULL,
direccion varchar(200) NOT NULL
);

CREATE TABLE Temporada (
id INT auto_increment primary key,
nombre varchar(50) not NULL,
fecha_inicio DATE NOT NULL,
fecha_fin  DATE NOT NULL
);

CREATE TABLE Clientes (
id INT auto_increment primary key,
nombre varchar(100) NOT NULL,
identificador varchar(255), -- Puede ser null porque los niños no deben porque tener obligatoriamente
fecha_nacimiento DATE NOT NULL

);