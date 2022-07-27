CREATE TABLE IF NOT EXISTS `payment_method` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `description` varchar(80) NOT NULL,
    `registration_update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;