CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(32) NOT NULL UNIQUE,
  `email_address` varchar(128) NOT NULL UNIQUE,
--  `identity` varchar(80) NOT NULL UNIQUE,
  `password` varchar(32) NOT NULL,
  `authority` int(11) NOT NULL DEFAULT '0',
  `account_lock` boolean,
  `account_expiration_date` date,
  `password_expiration_date` date,
  `account_group` text,
  `telephone1` varchar(15),
  `telephone2` varchar(15),
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
