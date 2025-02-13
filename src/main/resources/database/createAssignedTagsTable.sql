CREATE TABLE `assigned_tags` (
  `at_id` int NOT NULL AUTO_INCREMENT,
  `at_tag_id` int NOT NULL,
  `at_task_id` int NOT NULL,
  `at_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`at_id`),
  UNIQUE KEY `tag_task_combination_UNIQUE` (`at_tag_id`,`at_task_id`),
  KEY `task` (`at_task_id`),
  CONSTRAINT `tag` FOREIGN KEY (`at_tag_id`) REFERENCES `tags` (`tag_id`),
  CONSTRAINT `task` FOREIGN KEY (`at_task_id`) REFERENCES `tasks` (`task_id`)
)