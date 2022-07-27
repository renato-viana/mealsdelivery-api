CREATE TABLE IF NOT EXISTS `restaurant` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `cuisine_id` bigint(20) NOT NULL,
    `name` varchar(80) NOT NULL,
    `delivery_fee` decimal(10,2) NOT NULL,
    `active` tinyint(1) NOT NULL DEFAULT true,
    `open` tinyint(1) NOT NULL DEFAULT false,
    `registration_date` datetime NOT NULL,
    `registration_update_date` datetime NOT NULL,

    `address_city_id` bigint(20),
    `address_zip_code` varchar(9),
    `address_street` varchar(80),
    `address_number` varchar(20),
    `address_complement` varchar(80),
    `address_district` varchar(80),

    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table product add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_cuisine foreign key (cuisine_id) references cuisine (id);

alter table restaurant add constraint fk_restaurant_city foreign key (address_city_id) references city (id);