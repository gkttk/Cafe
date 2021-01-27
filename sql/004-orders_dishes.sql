create table if not exists orders_dishes
(
	order_id int not null,
	dish_id int not null,
	constraint fk_order
		foreign key (order_id) references orders (id)
			on update cascade on delete cascade,
	constraint fk_product
		foreign key (dish_id) references dishes (id)
			on update cascade
);

create index fk_order_idx
	on orders_dishes (order_id);

create index fk_product_idx
	on orders_dishes (dish_id);

