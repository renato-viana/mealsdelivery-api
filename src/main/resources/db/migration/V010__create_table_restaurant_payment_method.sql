CREATE TABLE IF NOT EXISTS `restaurant_payment_method` (
    `restaurant_id` bigint(20) NOT NULL,
    `payment_method_id` bigint(20) NOT NULL,

    PRIMARY KEY (`restaurant_id`, `payment_method_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table restaurant_payment_method add constraint fk_restaurant_payment_method_payment_method
foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method add constraint fk_rest_payment_method_restaurant
foreign key (restaurant_id) references restaurant (id);