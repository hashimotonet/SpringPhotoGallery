CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identity` varchar(100) NOT NULL,
  `authority` int(11) NOT NULL DEFAULT '0',
  `data` mediumblob NOT NULL,
  `thumbnail` mediumblob,
  `alt` varchar(200) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX(`identity`),
  FOREIGN KEY(`identity`) REFERENCES account(`identity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8