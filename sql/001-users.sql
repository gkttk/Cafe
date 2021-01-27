create table if not exists users
(
	id int auto_increment
		primary key,
	login varchar(50) not null,
	password varchar(255) null,
	role enum('USER', 'ADMIN') default 'USER' null,
	points int default 0 null,
	money decimal(10,2) default 0.00 not null,
	blocked tinyint default 0 null,
	avatar blob null,
	constraint LOGIN_UNIQUE
		unique (login)
);

