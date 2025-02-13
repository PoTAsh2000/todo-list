CREATE TABLE `statuses` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `status_name` varchar(16) NOT NULL,
  `status_hex_color` varchar(7) NOT NULL DEFAULT '#aaaaaa',
  `status_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`status_id`),
  UNIQUE KEY `status_name_UNIQUE` (`status_name`)
)