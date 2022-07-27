CREATE TABLE IF NOT EXISTS `state` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(80) NOT NULL,

    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table city add column state_id bigint not null;

alter table city add constraint fk_city_state foreign key (state_id) references state (id);