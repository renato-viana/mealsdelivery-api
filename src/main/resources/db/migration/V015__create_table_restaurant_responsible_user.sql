CREATE TABLE IF NOT EXISTS `restaurant_responsible_user` (
    `restaurant_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,

    PRIMARY KEY (`restaurant_id`, `user_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table restaurant_responsible_user add constraint fk_restaurant_responsible_user
foreign key (restaurant_id) references restaurant (id);

alter table restaurant_responsible_user add constraint fk_restaurant_user_user
foreign key (user_id) references user (id);