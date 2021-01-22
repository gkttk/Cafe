create table if not exists dishes
(
    id        int auto_increment,
    name      varchar(100)                                       not null,
    type      enum ('SOUP', 'SALAD', 'BEVERAGE')                 not null,
    cost      double(10, 2)                                      not null,
    image_ref varchar(100) default 'static/images/not_found.png' null,
    constraint name_UNIQUE
        unique (name),
    constraint product_id_UNIQUE
        unique (id)
);

alter table dishes
    add primary key (id);

create table if not exists users
(
    id         int auto_increment
        primary key,
    login      varchar(50)                           not null,
    password   varchar(50)                           not null,
    role       enum ('USER', 'ADMIN') default 'USER' null,
    points     int                    default 0      null,
    money      decimal(10, 2)         default 0.00   not null,
    blocked    tinyint                default 0      null,
    avatar blob                              null,
    constraint LOGIN_UNIQUE
        unique (login)
);

create table if not exists comments
(
    id            int auto_increment,
    text          varchar(250)                       not null,
    rating        int      default 0                 not null,
    creation_date datetime default CURRENT_TIMESTAMP null,
    user_id       int                                not null,
    dish_id       int                                null,
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

create table if not exists orders
(
    id      int auto_increment,
    cost    decimal(10, 2)                                                        not null,
    date    datetime                                                              not null,
    status  enum ('ACTIVE', 'RETRIEVED', 'CANCELLED', 'BLOCKED') default 'ACTIVE' null,
    user_id int                                                                   not null,
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

create table if not exists orders_dishes
(
    order_id int not null,
    dish_id  int not null,
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

create table if not exists users_comments_rating
(
    user_id    int        not null,
    comment_id int        not null,
    liked      tinyint(1) not null,
    primary key (user_id, comment_id)
);


