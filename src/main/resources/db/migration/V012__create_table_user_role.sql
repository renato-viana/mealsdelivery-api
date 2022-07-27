CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id` bigint(20) NOT NULL,
    `role_id` bigint(20) NOT NULL,

    PRIMARY KEY (`user_id`, `role_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table user_role add constraint fk_user_role_role
foreign key (role_id) references `role` (id);

alter table user_role add constraint fk_user_role_user
foreign key (user_id) references `user` (id);