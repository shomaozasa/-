CREATE TABLE `shifts` (
	`shift_id` INT AUTO_INCREMENT NOT NULL,
	`user_id` INT NOT NULL,
	`start_time` DATETIME NOT NULL,
	`end_time` DATETIME NOT NULL,
	`memo` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
	PRIMARY KEY (`shift_id`)
) ENGINE=InnoDB;