CREATE TABLE IF NOT EXISTS `role_permission` (
    `role_id` bigint(20) NOT NULL,
    `permission_id` bigint(20) NOT NULL,

    PRIMARY KEY (`role_id`, `permission_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table role_permission add constraint fk_role_permission_role
foreign key (role_id) references `role` (id);
