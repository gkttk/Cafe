create table if not exists dishes
(
	id int auto_increment,
	name varchar(100) not null,
	type enum('SOUP', 'SALAD', 'BEVERAGE') not null,
	cost double(10,2) not null,
	img longblob null,
	disabled tinyint(1) default 0 not null,
	constraint name_UNIQUE
		unique (name),
	constraint product_id_UNIQUE
		unique (id)
);

alter table dishes
	add primary key (id);

