//Tabla para diferenciar los tipos de usuario. Pueden ser Usuarios Normales o Administradores (quienes tienen sus privilegios)
create table tbTipoUsuario(
    UUID_TipoUsuario varchar2(50) primary key,
    nombreTipoUsuario varchar2(20) not null unique
);

drop table tbTipoUsuario

//Tabla para almacenar los usuarios
create table tbUsuarios(
    UUID_Usuario varchar2(50) primary key,
    UUID_TipoUsuario varchar2(50),
    nombreUsuario varchar2(100) not null unique,
    correoUsuario varchar2(100) not null,
    contrasenaUsuario varchar2(50) not null,
    huellaUsuario blob ,
    
    ----Llave foranea---
    constraint fk_UUID_TipoUsuario foreign key (UUID_TipoUsuario) references tbTipoUsuario (UUID_TipoUsuario),
    ----Restriccion para que la contraseña no sea menor a 8 caracteres----
    constraint chk_contrasena_min_length check (length(contrasenaUsuario) >= 8),                               
    ----Restriccion para que el correo tenga el formato correcto----
    constraint chk_correo_formato check (regexp_like(correoUsuario, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')),
    ----Restriccion para que el correo del usuario sea unico (importante al momento de recuperar contraseñas)---
    constraint uniq_correoUsuario unique (correoUsuario)
);

//Tabla para identificar si la categoria es de gasto o ingreso
create table tbTipoClasificacion(
    UUID_TipoClasificacion varchar2(50) primary key,
    nombreTipoClasificacion varchar2(50) not null unique
);

//Tabla para definir las clasificaciones de los gastos e ingresos. (Alimentacion, transporte, etc.)
create table tbClasificaciones(
    UUID_CLASIFICACIONES varchar2(50) primary key,
    UUID_Usuario varchar2(50),
    UUID_TipoClasificacion varchar2(50),
    nombreClasificacion varchar2(100) not null unique,
    
    ----Llave foranea para que cada usuario pueda tener sus propias categorias----    
    constraint fk_UUID_Usuarios foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),
    ----Llave para que el usuario decida si la categoria es para ingresos o gastos----
    constraint fk_UUID_TipoClasificacion foreign key (UUID_TipoClasificacion) references tbTipoClasificacion (UUID_TipoClasificacion)
);

//Tabla para identificar los tipos de gastos e ingresos del usuario en el presupuesto. (Fijo o variable)
create table tbTipoGastoIngreso(
    UUID_TipoGastoIngreso varchar2(50) primary key,
    nombreTipoGastoIngreso varchar2(100) not null unique
);

//Tabla para identificar la fuente del gasto
create table fuenteGasto(
    UUID_FuenteGasto varchar2(50) primary key,
     UUID_Gasto varchar2(50),
    nombreFuenteGasto varchar2(50) not null unique,
    CONSTRAINT fk_fuenteGasto foreign key (UUID_Gasto)References tbGastos(UUID_Gasto)
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

    constraint fk_UUID_Clasificacion foreign key (UUID_Clasificacion) references tbClasificaciones (UUID_Clasificaciones) 
);

//Tabla para almacenar los ingresos
create table tbIngresos(
    UUID_Ingreso varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    UUID_TipoGastoIngreso varchar2(50) not null,
    UUID_Clasificacion varchar2(50) not null,
    montoIngreso number(10,2) not null,
    fechaIngreso varchar2(20) not null,
    
    ----Para evitar que el valor de los ingresos sea un número negativo (menor a 0)----
    constraint chk_montoIngreso_non_negative check (montoIngreso >= 0),
    
    constraint fk_UUID_Usuario1 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),

    constraint fk_UUID_TipoGastoIngreso1 foreign key (UUID_TipoGastoIngreso) references tbTipoGastoIngreso (UUID_TipoGastoIngreso),

    constraint fk_UUID_Clasificacion1 foreign key (UUID_Clasificacion) references tbClasificaciones (UUID_Clasificaciones) 
);

//Tabla para manejar los ahorros
create table tbAhorros(
    UUID_Ahorro varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    montoAhorro number (10,2) not null,
    fechaAhorro varchar2(50) not null,

----Para evitar que el valor de los ahorros sea un número negativo (menor a 0)----
    constraint chk_montoAhorro_non_negative check (montoAhorro >= 0),
    
    constraint fk_UUID_Usuario2 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario)
);

//Tabla para crear un nuevo presupuesto
create table tbPresupuestos(
    UUID_Presupuesto varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    fechaInicio varchar2(50) not null,
    fechaFinal varchar2(50) not null,

    constraint fk_UUID_Usuario3 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario)
);

//Tabla para almacenar todos los detalles del presupuesto: ingresos, gastos y ahorros
create table tbDetallesPresupuesto(
    UUID_DetallePresupuesto varchar2(50) primary key,
    UUID_Presupuesto varchar2(50),
    UUID_Gasto varchar2(50) not null,
    UUID_Ingreso varchar2(50) not null,
    UUID_Ahorro varchar2(50) not null,
    
    constraint fk_UUID_Presupuesto foreign key (UUID_Presupuesto) references tbPresupuestos(UUID_Presupuesto),
    
    constraint fk_UUID_Gasto foreign key (UUID_Gasto) references tbGastos(UUID_Gasto),

    constraint fk_UUID_Ingreso foreign key (UUID_Ingreso) references tbIngresos(UUID_Ingreso),

    constraint fk_UUID_Ahorro foreign key (UUID_Ahorro) references tbAhorros (UUID_Ahorro)
);

//Tabla para definir tipo de recurso (video o lectura)
create table tbTipoRecursos(
    UUID_TipoRecurso int primary key,
    nombreTipoRecurso varchar2(50) not null unique
);

//Tabla para almacenar los recursos
create table tbRecursosEducativos(
    UUID_Recurso int primary key,
    UUID_TipoRecurso int not null unique,
    tituloRecurso varchar2(50) not null unique,
    descripcionRecurso varchar2(255) not null,
    urlRecurso varchar2(250) not null,
    miniaturaRecurso blob not null,

    constraint fk_UUID_TipoRecurso foreign key (UUID_TipoRecurso) references tbTipoRecursos (UUID_TipoRecurso)
);

//Tabla para las reseñas o valoraciones de los recursos (para evaluar el rendimiento del material)
create table tbValoraciones(
    UUID_Valoracion varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    UUID_Recurso int not null,
    rating number,
    comentarioRecurso varchar2(250),
    fechaValoracion varchar2(50) not null,
    
    constraint fk_UUID_Usuario4 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),
    constraint fk_UUID_Recurso foreign key (UUID_Recurso) references tbRecursosEducativos (UUID_Recurso)
);

//Tabla para definir metas financieras
create table tbMetasFinancieras(
    UUID_Meta int primary key,
    UUID_Usuario varchar2(50) not null,
    nombreMeta varchar2(100) not null,
    montoAhorrado number(10,2) default 0,
    montoObjetivo number(10,2) not null,
    fechaMeta varchar2(50) not null,
    
----Para evitar que el valor del monto Objetivo sea un número negativo (menor a 0)----
    constraint chk_montoObjetivo_non_negative check (montoObjetivo >= 0),
    
    constraint fk_UUID_Usuario5 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario)
);

//Tabla para que de los ahorros, se le asigne una parte a las metas financieras
create table tbAsignacionesAhorros(
    UUID_Asignacion varchar(50) primary key,
    UUID_Ahorro varchar(50) not null,
    UUID_Meta int not null,
    montoAsignado number(10,2) not null,
    fechaAsignacion varchar2(50) not null,
    
    ----Para evitar que el valor del monto Objetivo sea un número negativo (menor a 0)----
    constraint chk_montoAsignado_non_negative check (montoAsignado >= 0),
    
    constraint fk_UUID_Ahorro1 foreign key (UUID_Ahorro) REFERENCES tbAhorros (UUID_Ahorro),
    constraint fk_UUID_Meta foreign key (UUID_Meta) REFERENCES tbMetasFinancieras (UUID_Meta)
);

//Tabla para guardar las notificaciones, recordatorios y consejos
create table tbNotificaciones(
    UUID_Notificacion varchar2(50) primary key,
    UUID_Usuario varchar2(50) not null,
    UUID_Gasto varchar2(50) not null,
    fechaNotificacion varchar2(50) not null,
    horaNotificacion varchar2(50) not null,
    mensaje varchar2(255) not null,

    constraint fk_UUID_Usuario6 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),

    constraint fk_UUID_Gasto1 foreign key (UUID_Gasto) references tbGastos (UUID_Gasto)
);

create table tbRecursosFavoritos(
    UUID_RecursoFavorito int primary key,
    UUID_Usuario varchar2(50) not null,
    UUID_Recurso int not null,

    constraint fk_UUID_Usuario7 foreign key (UUID_Usuario) references tbUsuarios (UUID_Usuario),
    constraint fk_UUID_Recurso2 foreign key (UUID_Recurso) references tbRecursosEducativos(UUID_Recurso)
);