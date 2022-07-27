CREATE TABLE IF NOT EXISTS `product` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `restaurant_id` bigint(20) NOT NULL,
    `name` varchar(80) NOT NULL,
    `description` text NOT NULL,
    `price` decimal(10,2) NOT NULL,
    `active` tinyint(1) NOT NULL,

    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;