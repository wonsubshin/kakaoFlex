DROP TABLE IF EXISTS flex_master;
DROP TABLE IF EXISTS flex_detail;

create table flex_master (
token char(3) not null primary key,
roomId varchar(255),
userId varchar(255),
createDate datetime not null,
totalMoney integer not null,
people integer not null
);


CREATE TABLE `kakaopay`.`flex_detail` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TOKEN` CHAR(3)  NOT NULL,
  `MONEY` INT NULL,
  `UPDATE_TIME` DATETIME NULL,
  `USER_ID` VARCHAR(45) NULL,
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC) VISIBLE,
  PRIMARY KEY (`ID`),
  CONSTRAINT `tokenFk`
    FOREIGN KEY (TOKEN)
    REFERENCES `kakaopay`.`flex_master` (TOKEN)
    );
