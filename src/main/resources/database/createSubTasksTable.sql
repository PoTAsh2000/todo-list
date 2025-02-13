CREATE TABLE `sub_tasks` (
  `sub_id` int NOT NULL AUTO_INCREMENT,
  `sub_task_id` int NOT NULL,
  `sub_name` varchar(64) NOT NULL,
  `sub_status` int NOT NULL,
  `sub_deadline_date` datetime DEFAULT NULL,
  `sub_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sub_id`),
  UNIQUE KEY `sub_id_UNIQUE` (`sub_id`),
  UNIQUE KEY `sub_name_UNIQUE` (`sub_name`),
  KEY `sub_status_idx` (`sub_status`),
  CONSTRAINT `sub_status` FOREIGN KEY (`sub_status`) REFERENCES `statusses` (`status_id`)
)