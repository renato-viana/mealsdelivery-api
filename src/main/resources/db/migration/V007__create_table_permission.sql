CREATE TABLE IF NOT EXISTS `permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(80) NOT NULL,
    `description` varchar(80) NOT NULL,

    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table role_permission add constraint fk_role_permission_permission
foreign key (permission_id) references permission (id);