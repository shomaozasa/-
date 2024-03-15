CREATE TABLE `users` (
	`user_id` INT AUTO_INCREMENT NOT NULL,
	`job_id` INT NOT NULL,
	`user_name` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	`password` CHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	`age` INT NOT NULL,
	`phone` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
	`email` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci UNIQUE,
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

