-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema GoDrink_DB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GoDrink_DB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GoDrink_DB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema GoDrink_DB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GoDrink_DB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GoDrink_DB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `GoDrink_DB` ;
USE `GoDrink_DB` ;

-- -----------------------------------------------------
-- Table `GoDrink_DB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GoDrink_DB`.`User` (
  `userID` INT NOT NULL,
  `userName` VARCHAR(60) NOT NULL,
  `userEmail` VARCHAR(150) NOT NULL,
  `userPassword` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`userID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `GoDrink_DB`.`Preferences`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GoDrink_DB`.`Preferences` (
  `preferenceID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`preferenceID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GoDrink_DB`.`userPreferences`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GoDrink_DB`.`userPreferences` (
  `userID` INT NOT NULL,
  `preferenceID` INT NOT NULL,
  PRIMARY KEY (`userID`, `preferenceID`),
  INDEX `fk_User_has_Preferences_Preferences1_idx` (`preferenceID` ASC) VISIBLE,
  INDEX `fk_User_has_Preferences_User_idx` (`userID` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_Preferences_User`
    FOREIGN KEY (`userID`)
    REFERENCES `GoDrink_DB`.`User` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Preferences_Preferences1`
    FOREIGN KEY (`preferenceID`)
    REFERENCES `GoDrink_DB`.`Preferences` (`preferenceID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
