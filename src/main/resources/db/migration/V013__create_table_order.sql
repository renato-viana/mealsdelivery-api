CREATE TABLE IF NOT EXISTS `order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `code` varchar(36) NOT NULL DEFAULT (uuid()),
    `subtotal` decimal(10,2) NOT NULL,
    `delivery_fee` decimal(10,2) NOT NULL,
    `total_price` decimal(10,2) NOT NULL,

    `restaurant_id` bigint(20) NOT NULL,
    `user_customer_id` bigint(20) NOT NULL,
    `payment_method_id` bigint(20) NOT NULL,

    `address_city_id` bigint(20) NOT NULL,
    `address_zip_code` varchar(9) NOT NULL,
    `address_street` varchar(80) NOT NULL,
    `address_number` varchar(20) NOT NULL,
    `address_complement` varchar(80) NULL,
    `address_district` varchar(80) NOT NULL,

    `status` varchar(10) NOT NULL,
    `creation_date` datetime NOT NULL,
    `confirmation_date` datetime NULL,
    `cancellation_date` datetime NULL,
    `delivery_date` datetime NULL,

    PRIMARY KEY (`id`),

    constraint fk_order_address_city foreign key (address_city_id) references city (id),
    constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_user_customer foreign key (user_customer_id) references user (id),
    constraint fk_order_payment_method foreign key (payment_method_id) references payment_method (id),
    constraint uk_order_code unique (code)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;