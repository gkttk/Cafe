create table if not exists orders
(
	id int auto_increment,
	cost decimal(10,2) not null,
	date datetime not null,
	status enum('ACTIVE', 'RETRIEVED', 'CANCELLED', 'BLOCKED') default 'ACTIVE' null,
	user_id int not null,
	constraint idORDER_UNIQUE
		unique (id),
	constraint fk_order_user
		foreign key (user_id) references users (id)
			on update cascade on delete cascade
);

create index fk_order_user_idx
	on orders (user_id);

alter table orders
	add primary key (id);

