DROP TABLE IF EXISTS flex_master;
DROP TABLE IF EXISTS flex_detail;

CREATE TABLE `kakaopay`.`flex_master` (
  `TOKEN` char(3) NOT NULL,
  `ROOM_ID` varchar(255) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `TOTAL_MONEY` int NOT NULL,
  `PEOPLE` int NOT NULL,
  PRIMARY KEY (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;


CREATE TABLE `flex_detail` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `TOKEN` char(3) NOT NULL,
  `MONEY` int DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `USER_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `id_UNIQUE` (`ID`),
  KEY `tokenFk` (`TOKEN`),
  CONSTRAINT `tokenFk` FOREIGN KEY (`TOKEN`) REFERENCES `flex_master` (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;