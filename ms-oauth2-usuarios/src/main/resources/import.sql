insert into usuarios (username,password,enabled,nombre,apellido,email) values('mpoblete','123456',1,'Manuel','Poblete','mpobletemori@gmail.com');
insert into usuarios (username,password,enabled,nombre,apellido,email) values('mspoblete','123456',1,'Manuel Santiago','Poblete','mspobletemori@gmail.com');

insert into roles(nombre) values('ROLE_USER');
insert into roles(nombre) values('ROLE_ADMIN');

insert into usuarios_roles(usuario_id,role_id) values(1,1);
insert into usuarios_roles(usuario_id,role_id) values(2,1);
insert into usuarios_roles(usuario_id,role_id) values(2,2);

