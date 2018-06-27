create schema FullCoveragetestcase;
CREATE TABLE `FullCoveragetestcase.table01` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Field01` int(11) DEFAULT NULL,
  `Field02` varchar(45) DEFAULT NULL,
  `Field03` double DEFAULT NULL,
  `Field04` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE `FullCoveragetestcase.table02` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Field01` int(11) DEFAULT NULL,
  `Field02` varchar(45) DEFAULT NULL,
  `Field03` double DEFAULT NULL,
  `Field04` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `FullCoveragetestcase`.`query01` AS select `FullCoveragetestcase`.`table01`.`Field01` AS `Field01` from `FullCoveragetestcase`.`table01`;

