--version h2
--insert into usuarios (username,password,enabled,nombre,apellido,email) values('mpoblete','$2a$10$FrVwmKc4ckz54yHmWimDVuaHctlFvLlLwLhoLrGTIA5JOQazGOxha',1,'Manuel','Poblete','mpobletemori@gmail.com');
--insert into usuarios (username,password,enabled,nombre,apellido,email) values('mspoblete','$2a$10$F/aJCJhYwDQghxcMeSgKruJyANUPtfI7SC7iklTqHmV7ahE0YQB9K',1,'Manuel Santiago','Poblete','mspobletemori@gmail.com');

--insert into roles(nombre) values('ROLE_USER');
--insert into roles(nombre) values('ROLE_ADMIN');

--insert into usuarios_roles(usuario_id,role_id) values(1,1);
--insert into usuarios_roles(usuario_id,role_id) values(2,1);
--insert into usuarios_roles(usuario_id,role_id) values(2,2);

--version postgresql
insert into usuarios (username,password,enabled,nombre,apellido,email) values('mpoblete','$2a$10$FrVwmKc4ckz54yHmWimDVuaHctlFvLlLwLhoLrGTIA5JOQazGOxha',true,'Manuel','Poblete','mpobletemori@gmail.com');
insert into usuarios (username,password,enabled,nombre,apellido,email) values('mspoblete','$2a$10$F/aJCJhYwDQghxcMeSgKruJyANUPtfI7SC7iklTqHmV7ahE0YQB9K',true,'Manuel Santiago','Poblete','mspobletemori@gmail.com');

insert into roles(nombre) values('ROLE_USER');
insert into roles(nombre) values('ROLE_ADMIN');

insert into usuarios_roles(usuario_id,role_id) values(1,1);
insert into usuarios_roles(usuario_id,role_id) values(2,1);
insert into usuarios_roles(usuario_id,role_id) values(2,2);

