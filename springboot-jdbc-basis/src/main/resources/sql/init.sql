CREATE DATABASE IF NOT EXISTS `testdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `testdb`;

CREATE TABLE IF NOT EXISTS person
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`          VARCHAR(50) NOT NULL COMMENT '이름',
    `email`         VARCHAR(50) NOT NULL COMMENT '이메일',
    `remark`        VARCHAR(30) NULL COMMENT '리마크',
    `created_date`  DATE NOT NULL COMMENT '생성일',
    `created_time`  TIME NOT NULL COMMENT '생성시간',
    PRIMARY KEY (`id`),
    unique KEY (`name`, `email`)
)   COMMENT = '사람' ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;