create database poo_jpa;
use poo_jpa;

create table cliente(
id int not null auto_increment,
cpf varchar(20),
nome varchar(60),
conta_corrente_numero varchar(60),
conta_poupanca_numero varchar(60),
primary key (id)
);


create table conta_corrente(
id int not null auto_increment,
numero_conta varchar(60),
saldo float,
data_criacao timestamp default current_timestamp,
status boolean,
primary key (id)
);

create table conta_poupanca(
id int not null auto_increment,
numero_conta varchar(60),
saldo float,
data_criacao timestamp default current_timestamp,
status boolean,
primary key (id)
);

create table registro_transacao(
id int not null auto_increment,
numero_conta varchar(60),
valor float,
tipo_conta varchar(20),
tipo_transacao varchar(50),
data_transacao timestamp default current_timestamp,
primary key(id) 
);

