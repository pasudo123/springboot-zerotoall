use testdb;

CREATE TABLE IF NOT EXISTS `member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_uniq_id` VARCHAR(100) NULL,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `member_uniq_id_UNIQUE` (`member_uniq_id` ASC) VISIBLE);
