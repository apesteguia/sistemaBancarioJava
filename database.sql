create table usuario
(
	nombre varchar(30) not null,
    ap1 varchar(30) not null,
    ap2 varchar(30) not null,
    telefono int not null unique ,
    dni varchar(9) primary key not null unique ,
    direccion varchar(30) not null,
    correo varchar(50) unique,
    contr varchar(20) not null,
    dinero varchar(20) default 0
);

create table transacciones 
(
	remitente varchar(20) not null default "anonimo",
	fecha date,
    tipo varchar(20) not null,
    cantidad long not null,
    cod varchar(9) not null,
    id int not null auto_increment,
    foreign key (cod) references usuario(dni),
    primary key (id)
);

insert usuario (nombre, ap1, ap2, telefono, dni, direccion, correo, contr, dinero) values ("Mikel", "Apesteguia", "Guillen", 620037759, "73455730D", "Estella"
, "mikeltxomikeltxo@gmail.com", "123", 10000000);

insert usuario (nombre, ap1, ap2, telefono, dni, direccion, correo, contr, dinero) values ("Ana", "Guillen", "Laset", 646325456, "32421456S", "Estella"
, "anuska.agl@gmail.com", "123", 20000000);


insert transacciones (fecha, tipo, cantidad, cod) values (sysdate(), "retirada", 100, "73455730D");
insert transacciones (remitente, fecha, tipo, cantidad, cod) values ("Ana" ,sysdate(), "ingreso", 34200, "73455730D");
insert transacciones (remitente, fecha, tipo, cantidad, cod) values ("Mikel" ,sysdate(), "retirada", 20, "32421456S");

select * from transacciones;
select transacciones.cod from transacciones join usuario on transacciones.cod = usuario.dni
where usuario.nombre="Ana";

