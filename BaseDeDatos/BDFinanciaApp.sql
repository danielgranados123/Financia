//Tabla para diferenciar los tipos de usuario. Pueden ser Usuarios Normales o Administradores (quienes tienen sus privilegios)
create table tbTipoUsuario(
    UUID_TipoUsuario varchar2(50) primary key,
    nombre varchar2(20)
);

//Tabla para almacenar los usuarios
create table tbUsuarios(
UUID_Usuario varchar2(50) primary key,
nombre varchar2(100),
correo varchar2(100),
contraseña varchar2(50),
huella BLOB
);

//Tabla para definir las clasificaciones de los gastos e ingresos. (Alimentacion, transporte, etc.)
create table tbClasificaciones(
UUID_CLASIFICACIONES int primary key,
nombre varchar2(100)
);

//Tabla para identificar los tipos de gastos e ingresos del usuario en el presupuesto. (Fijo o variable)
create table tbTipoGastoIngreso(
UUID_TipoGastoIngreso int primary key,
nombre varchar2(100)
);

create table tbGastos(
idGasto int primary key,
idUsuario int,
idTipoGastoIngreso int,
idClasificacion int,
montoGasto number(10,2),
fechaGasto date,

constraint fk_idUsuario foreign key (idUsuario) references tbUsuarios (idUsuario),

constraint fk_idTipoGastoIngreso foreign key (idTipoGastoIngreso) references tbTipoGastoIngreso (idTipoGastoIngreso),

constraint fk_idClasificacion foreign key (idClasificacion) references tbClasificaciones (idClasificacion) 
);

create table tbIngresos(
idIngreso int primary key,
idUsuario int,
idTipoGastoIngreso int,
idClasificacion int,
montoIngreso number(10,2),
fechaIngreso date,
constraint fk_idUsuario1 foreign key (idUsuario) references tbUsuarios (idUsuario),

constraint fk_idTipoGastoIngreso1 foreign key (idTipoGastoIngreso) references tbTipoGastoIngreso (idTipoGastoIngreso),

constraint fk_idClasificacion1 foreign key (idClasificacion) references tbClasificaciones (idClasificacion) 
);

create table tbAhorros(
idAhorro int primary key,
idUsuario int,
montoAhorro number (10,2),
fecha date,

constraint fk_idUsuario2 foreign key (idUsuario) references tbUsuarios (idUsuario)
);

create table tbPresupuesto(
idPresupuesto int primary key,
idUsuario int,
idGasto int,
idIngreso int,
idAhorro int,
fechaInicio date,
fechaFinal date,

constraint fk_idUsuario3 foreign key (idUsuario) references tbUsuarios (idUsuario),

constraint fk_Gasto foreign key (idGasto) references tbGastos(idGasto),

constraint fk_idIngreso foreign key (idIngreso) references tbIngresos(idIngreso),

constraint fk_idAhorro foreign key (idAhorro) references tbAhorros (idAhorro)
);

create table tbTipoRecursos(
idTipoRecurso int primary key,
nombre varchar2(50)
);

create table tbRecursosEducativos(
idRecurso int primary key,
idTipoRecurso int,
titulo varchar2(50),
descripcion varchar2(255),
url varchar2(100),
imagen blob,
valoracion int,

constraint fk_idTipoRecurso foreign key (idTipoRecurso) references tbTipoRecursos (idTipoRecurso)
);

create table tbMetasFinancieras(
idMetas int primary key,
idUsuario int,
nombre varchar2(100),
descripcion varchar2(255),
montoMeta number(10,2),
fechaMeta date,

constraint fk_idUsuario4 foreign key (idUsuario) references tbUsuarios (idUsuario)
);

create table tbNotificaciones(
idNotificacion int primary key,
idUsuario int,
idGasto int,
fechaNotificacion date,
horaNotificacion TIMESTAMP,
mensaje varchar2(255),

constraint fk_idUsuario5 foreign key (idUsuario) references tbUsuarios (idUsuario),

constraint fk_idGasto foreign key (idGasto) references tbGastos (idGasto)
);

create table tbRecursosFavoritos(
idRecursoFavorito int primary key,
idUsuario int,
idRecurso int,

constraint fk_idUsuario6 foreign key (idUsuario) references tbUsuarios (idUsuario),
constraint fk_idRecurso foreign key (idRecurso) references tbRecursosEducativos(idRecurso)
);