create table if not exists comments
(
	id int auto_increment,
	text varchar(250) not null,
	rating int default 0 not null,
	creation_date datetime default CURRENT_TIMESTAMP null,
	user_id int not null,
	dish_id int null,
	constraint comment_id_UNIQUE
		unique (id),
	constraint fk_product_id
		foreign key (dish_id) references dishes (id)
			on update cascade on delete cascade,
	constraint fk_user_id
		foreign key (user_id) references users (id)
			on update cascade
);

create index fk_product_id_idx
	on comments (dish_id);

create index fk_user_id_idx
	on comments (user_id);

alter table comments
	add primary key (id);

