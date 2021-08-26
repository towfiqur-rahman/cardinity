CREATE TABLE IF NOT EXISTS `user_type` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`authority` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
	`role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`account_non_expired` bit(1) NOT NULL,
	`account_non_locked` bit(1) NOT NULL,
	`credentials_non_expired` bit(1) NOT NULL,
	`enabled` bit(1) NOT NULL,
	`password` varchar(255) NOT NULL,
	`username` varchar(255) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE (`username`)
);