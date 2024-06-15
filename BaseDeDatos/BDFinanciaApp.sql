//Tabla para diferenciar los tipos de usuario. Pueden ser Usuarios Normales o Administradores (quienes tienen sus privilegios)
create table tbTipoUsuario(
    UUID_TipoUsuario varchar2(50) primary key,
    nombreTipoUsuario varchar2(20) not null unique
);

//Tabla para almacenar los usuarios
create table tbUsuarios(
    UUID_Usuario varchar2(50) primary key,
    UUID_TipoUsuario varchar2(50),
    nombreUsuario varchar2(100) not null,
    correoUsuario varchar2(100) not null,
    contrasenaUsuario varchar2(50) not null,
    huellaUsuario blob unique,
    
    ----Llave foranea---
    constraint fk_UUID_TipoUsuario foreign key (UUID_TipoUsuario) references tbTipoUsuario (UUID_TipoUsuario),
    ----Restriccion para que la contraseña no sea menor a 8 caracteres----
    constraint chk_contrasena_min_length check (length(contrasenaUsuario) >= 8),                               
    ----Restriccion para que el correo tenga el formato correcto----
    constraint chk_correo_formato check (regexp_like(correoUsuario, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')),
    ----Restriccion para que el correo del usuario sea unico (importante al momento de recuperar contraseñas)---
    constraint uniq_correoUsuario unique (correoUsuario)
);

//Tabla para definir las clasificaciones de los gastos e ingresos. (Alimentacion, transporte, etc.)
create table tbClasificaciones(
    UUID_CLASIFICACIONES varchar2(50) primary key,
    nombreClasificacion varchar2(100) not null unique
);

//Tabla para identificar los tipos de gastos e ingresos del usuario en el presupuesto. (Fijo o variable)
create table tbTipoGastoIngreso(
    UUID_TipoGastoIngreso varchar2(50) primary key,
    nombreTipoGastoIngreso varchar2(100) not null unique
);

//Tabla para almacenar los gastos del usuario
create table tbGastos(
    UUID_Gasto varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    UUID_TipoGastoIngreso varchar2(50) not null,
    UUID_Clasificacion varchar2(50) not null,
    montoGasto number(10,2) not null,
    fechaGasto varchar2(20) not null,

    ----Para evitar que el valor de los gastos sea un número negativo (menor a 0)----
    constraint chk_montoGasto_non_negative check (montoGasto >= 0),
    
    constraint fk_UUID_Usuario foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),

    constraint fk_UUID_TipoGastoIngreso foreign key (UUID_TipoGastoIngreso) references tbTipoGastoIngreso (UUID_TipoGastoIngreso),

    constraint fk_UUID_Clasificacion foreign key (UUID_Clasificacion) references tbClasificaciones (UUID_Clasificacion) 
);

//Tabla para almacenar los ingresos
create table tbIngresos(
    UUID_Ingreso varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    UUID_TipoGastoIngreso varchar2(50) not null,
    UUID_Clasificacion varchar2(50) not null,
    montoIngreso number(10,2),
    fechaIngreso varchar2(20) not null,
    
    ----Para evitar que el valor de los ingresos sea un número negativo (menor a 0)----
    constraint chk_montoIngreso_non_negative check (montoIngreso >= 0),
    
    constraint fk_UUID_Usuario foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),

    constraint fk_UUID_TipoGastoIngreso foreign key (UUID_TipoGastoIngreso) references tbTipoGastoIngreso (UUID_TipoGastoIngreso),

    constraint fk_UUID_Clasificacion foreign key (UUID_Clasificacion) references tbClasificaciones (UUID_Clasificacion) 
);

//Tabla para manejar los ahorros
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