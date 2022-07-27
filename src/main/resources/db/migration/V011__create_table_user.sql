CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(80) NOT NULL,
    `email` varchar(80) NOT NULL,
    `password` varchar(80) NOT NULL,
    `registration_date` datetime NOT NULL,

    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;