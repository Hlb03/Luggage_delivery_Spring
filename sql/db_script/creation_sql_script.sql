-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema luggage_delivery
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `luggage_delivery`;

-- -----------------------------------------------------
-- Schema luggage_delivery
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `luggage_delivery` DEFAULT CHARACTER SET utf8mb3;
USE `luggage_delivery`;

-- -----------------------------------------------------
-- Table `luggage_delivery`.`route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`route`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`route`
(
    `id`                INT            NOT NULL AUTO_INCREMENT,
    `start_point`       VARCHAR(45)    NOT NULL,
    `destination_point` VARCHAR(45)    NOT NULL,
    `distance`          DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 23
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `luggage_delivery`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`status`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`status`
(
    `name` VARCHAR(45) NOT NULL,
    UNIQUE INDEX `status_name_UNIQUE` (`name` ASC) VISIBLE,
    PRIMARY KEY (`name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `luggage_delivery`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`role`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`role`
(
    `name` VARCHAR(45) NOT NULL,
    UNIQUE INDEX `role_name_UNIQUE` (`name` ASC) VISIBLE,
    PRIMARY KEY (`name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `luggage_delivery`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`user`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`user`
(
    `id`          INT           NOT NULL AUTO_INCREMENT,
    `first_name`  VARCHAR(45)   NOT NULL,
    `last_name`   VARCHAR(45)   NOT NULL,
    `login`       VARCHAR(45)   NOT NULL,
    `password`    VARCHAR(256)   NOT NULL,
    `balance`     DECIMAL(6, 2) NOT NULL,
    `status_name` VARCHAR(45)   NOT NULL,
    `role_name`   VARCHAR(45)   NOT NULL,
    PRIMARY KEY (`id`, `login`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    INDEX `fk_user_status1_idx` (`status_name` ASC) VISIBLE,
    INDEX `fk_user_role1_idx` (`role_name` ASC) VISIBLE,
    CONSTRAINT `fk_user_status1`
        FOREIGN KEY (`status_name`)
            REFERENCES `luggage_delivery`.`status` (`name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_role1`
        FOREIGN KEY (`role_name`)
            REFERENCES `luggage_delivery`.`role` (`name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `luggage_delivery`.`delivery_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`delivery_status`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`delivery_status`
(
    `name` VARCHAR(45) NOT NULL,
    UNIQUE INDEX `status_name_UNIQUE` (`name` ASC) VISIBLE,
    PRIMARY KEY (`name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 10
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `luggage_delivery`.`delivery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`delivery`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`delivery`
(
    `id`                   INT           NOT NULL AUTO_INCREMENT,
    `luggage_size`         DECIMAL(5, 2) NOT NULL,
    `total_price`          DECIMAL(5, 2) NOT NULL,
    `luggage_type`         VARCHAR(45)   NOT NULL,
    `weight`               DECIMAL(5, 2) NOT NULL,
    `start_date`           DATE          NOT NULL,
    `delivery_date`        DATE          NOT NULL,
    `delivery_address`     VARCHAR(45)   NOT NULL,
    `routes_id`            INT           NOT NULL,
    `user_id`              INT           NOT NULL,
    `delivery_status_name` VARCHAR(45)   NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_delivery_routes1_idx` (`routes_id` ASC) VISIBLE,
    INDEX `fk_delivery_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_delivery_delivery_status1_idx` (`delivery_status_name` ASC) VISIBLE,
    CONSTRAINT `fk_delivery_routes1`
        FOREIGN KEY (`routes_id`)
            REFERENCES `luggage_delivery`.`route` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_delivery_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `luggage_delivery`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_delivery_delivery_status1`
        FOREIGN KEY (`delivery_status_name`)
            REFERENCES `luggage_delivery`.`delivery_status` (`name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `luggage_delivery`.`tariff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `luggage_delivery`.`tariff`;

CREATE TABLE IF NOT EXISTS `luggage_delivery`.`tariff`
(
    `id`    INT           NOT NULL AUTO_INCREMENT,
    `type`  VARCHAR(45)   NOT NULL,
    `price` DECIMAL(3, 2) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `type_UNIQUE` (`type` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 9
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------
-- insert into `role` table
-- -----------------------------------
INSERT INTO `role` (`name`)
VALUES ('USER'),
       ('MANAGER');

-- -----------------------------------
-- insert into `status` table
-- -----------------------------------
INSERT INTO `status` (`name`)
VALUES ('ACTIVE'),
       ('BLOCKED');

-- -----------------------------------
-- insert into `tariff` table
-- -----------------------------------
INSERT INTO `tariff` (`type`, `price`)
VALUES ('FRAGILE', '1.45'),
       ('DISTANCE', '0.45'),
       ('SIZE', '0.60'),
       ('WEIGHT', '0.45');

-- -----------------------------------
-- insert into `delivery_status` table
-- -----------------------------------
INSERT INTO `delivery_status` (`name`)
VALUES ('PROCESSING'),
       ('REJECTED'),
       ('PAY'),
       ('IN PROGRESS');

-- -----------------------------------
-- insert into `route` table
-- -----------------------------------
INSERT INTO `route` (`start_point`, `destination_point`, `distance`)
VALUES ('Kyiv', 'Lviv', '542.30'),
       ('Kyiv', 'Rivne', '342.40'),
       ('Kyiv', 'Khmelnytskyi', '353.10'),
       ('Kyiv', 'Odessa', '475.70'),
       ('Kyiv', 'Kherson', '611.20');


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;