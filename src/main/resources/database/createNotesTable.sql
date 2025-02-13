CREATE TABLE `notes` (
  `note_id` int NOT NULL AUTO_INCREMENT,
  `note_value` text NOT NULL,
  `note_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`note_id`)
)