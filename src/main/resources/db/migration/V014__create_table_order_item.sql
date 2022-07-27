CREATE TABLE IF NOT EXISTS `order_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `amount` smallint(6) NOT NULL,
    `unit_price` decimal(10,2) NOT NULL,
    `total_price` decimal(10,2) NOT NULL,
    `note` text NULL,
    `order_id` bigint(20) NOT NULL,
    `product_id` bigint(20) NOT NULL,

    PRIMARY KEY (`id`),
    unique key uk_order_item_product (`order_id`, `product_id`),
    
    constraint fk_order_item_order foreign key (order_id) references `order` (id),
    constraint fk_order_item_product foreign key (product_id) references product (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;