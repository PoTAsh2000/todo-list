CREATE TABLE `tasks` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `task_name` varchar(64) NOT NULL,
  `task_status` int NOT NULL,
  `task_priority` int DEFAULT NULL,
  `task_deadline_date` datetime DEFAULT NULL,
  `task_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `task_id_UNIQUE` (`task_id`),
  UNIQUE KEY `task_name_UNIQUE` (`task_name`),
  KEY `status_idx` (`task_status`),
  CONSTRAINT `status` FOREIGN KEY (`task_status`) REFERENCES `statusses` (`status_id`)
)