CREATE TABLE IF NOT EXISTS `product_image` (
    `product_id` bigint(20) NOT NULL,
    `file_name` varchar(80) NOT NULL,
    `description` text NOT NULL,
    `content_type` varchar(80) NOT NULL,
    `size` int NOT NULL,

    PRIMARY KEY (`product_id`),

    CONSTRAINT fk_product_image_product FOREIGN KEY (product_id) REFERENCES product (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;