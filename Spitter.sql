/*
SQLyog Ultimate v8.53 
MySQL - 5.7.12-log : Database - spitter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spitter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spitter`;

/*Table structure for table `spitter` */

DROP TABLE IF EXISTS `spitter`;

CREATE TABLE `spitter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `spitter` */

insert  into `spitter`(`id`,`email`,`firstname`,`lastName`,`password`,`role`,`username`) values (1,'yjfruby@126.com','Tyler','Yin','ed146279b389b31558c89a714e5b6a6a','ADMIN','yjfruby'),(2,'279935400@qq.com','qqqq','wwww','ed146279b389b31558c89a714e5b6a6a',NULL,'dddddddd'),(3,'279935400@qq.com','hhhhhh','kkkkkkkk','ed146279b389b31558c89a714e5b6a6a',NULL,'mmmmmmmmm'),(4,'279935400@qq.com','vvvvvvvv','vvvvvvvv','ed146279b389b31558c89a714e5b6a6a',NULL,'vvvvvvvvvv');

/*Table structure for table `spittle` */

DROP TABLE IF EXISTS `spittle`;

CREATE TABLE `spittle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `postedTime` datetime DEFAULT NULL,
  `spitter` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEBC21B8562F013AE` (`spitter`),
  CONSTRAINT `FKEBC21B8562F013AE` FOREIGN KEY (`spitter`) REFERENCES `spitter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `spittle` */

insert  into `spittle`(`id`,`message`,`postedTime`,`spitter`) values (1,'test message one','2107-02-14 00:00:00',1),(2,'test message two','2107-02-14 00:00:00',1),(3,'test message three','2107-02-14 00:00:00',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
