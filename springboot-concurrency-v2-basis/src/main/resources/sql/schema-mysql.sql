CREATE TABLE `testdb`.`posts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `contents` TEXT NOT NULL,
  `votes` BIGINT NULL,
PRIMARY KEY (`id`));
