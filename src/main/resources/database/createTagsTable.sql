CREATE TABLE `tags` (
  `tag_id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(16) NOT NULL,
  `tag_hex_color` varchar(7) NOT NULL DEFAULT '#aaaaaa',
  `tag_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag_name_UNIQUE` (`tag_name`)
)