create table if not exists users_comments_rating
(
	user_id int not null,
	comment_id int not null,
	liked tinyint(1) not null,
	primary key (user_id, comment_id),
	constraint comment_id_fk
		foreign key (comment_id) references comments (id)
			on delete cascade,
	constraint user_id_fk
		foreign key (user_id) references users (id)
			on delete cascade
);

create index comment_id_fk_idx
	on users_comments_rating (comment_id);

