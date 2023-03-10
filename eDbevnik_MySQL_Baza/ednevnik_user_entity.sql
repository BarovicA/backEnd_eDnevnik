-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ednevnik
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_entity`
--

DROP TABLE IF EXISTS `user_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_entity` (
  `user_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` int DEFAULT NULL,
  `grade` bigint DEFAULT NULL,
  `parent` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf8223oa1hxgpe7cksr0ok0ucv` (`role`),
  KEY `FK4ax9o63o22jb7ff11m27vd37f` (`grade`),
  KEY `FK9jabn1eb5unpxd1efygnc28kb` (`parent`),
  CONSTRAINT `FK4ax9o63o22jb7ff11m27vd37f` FOREIGN KEY (`grade`) REFERENCES `grade_entity` (`id`),
  CONSTRAINT `FK9jabn1eb5unpxd1efygnc28kb` FOREIGN KEY (`parent`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_entity`
--

LOCK TABLES `user_entity` WRITE;
/*!40000 ALTER TABLE `user_entity` DISABLE KEYS */;
INSERT INTO `user_entity` VALUES ('Teacher',6,_binary '\0','Milica','Milic','$2a$12$ysW66jEBc5Q1RY.VuP9SH.TQpScr81ehP7MLuYfk0Dsiao3cp4JHK','milica123',2,NULL,45,NULL,NULL),('Teacher',7,_binary '\0','Zorica','Zoric','$2a$12$ysW66jEBc5Q1RY.VuP9SH.TQpScr81ehP7MLuYfk0Dsiao3cp4JHK','zorica123',2,NULL,45,NULL,NULL),('Teacher',8,_binary '\0','Radica','Radic','$2a$12$ysW66jEBc5Q1RY.VuP9SH.TQpScr81ehP7MLuYfk0Dsiao3cp4JHK','radica123',2,NULL,45,NULL,NULL),('Student',9,_binary '\0','Stevica','Kurcubic','$2a$12$KMG/wRS4p4QCx4L84S6PF.Zzxb1PFZlYd5XcxtIaMJqmRuHy8/x02','stevica123',4,NULL,43,17,12),('Student',10,_binary '\0','Perica','Peric','$2a$12$KMG/wRS4p4QCx4L84S6PF.Zzxb1PFZlYd5XcxtIaMJqmRuHy8/x02','perica123',4,NULL,43,17,151),('Student',11,_binary '\0','Ljubica','Drazic','$2a$12$KMG/wRS4p4QCx4L84S6PF.Zzxb1PFZlYd5XcxtIaMJqmRuHy8/x02','ljubica123',4,NULL,43,17,13),('Parent',12,_binary '\0','Katarina','Drazic','$2a$12$ggYcIyfA1wq5uHfkS4oDm.HtIz9hEWH5Hofg06uFNX8LvJ0zKffIC','katarina123',1,'katarinadrazic22@gmail.com',42,NULL,NULL),('Parent',13,_binary '\0','Radoje','Radic','$2a$12$ggYcIyfA1wq5uHfkS4oDm.HtIz9hEWH5Hofg06uFNX8LvJ0zKffIC','radoje123',1,'radoje@mail.com',42,NULL,NULL),('Admin',48,_binary '\0','Admin','Admin','$2a$10$DnuRBAGtSnvC0Mr/VJroHexMUFjnvN7HZwZd4ICnj8sjinIbHf4t.','admin',0,NULL,44,NULL,NULL),('Parent',49,_binary '\0','Miloje','Milojevic','$2a$10$KCxWBrGyQlw6kVE6dbzlv.27qDtbIqF4lABHN2w6ZAuH3THsVVUwW','miloje123',0,'aleksa.barovic.brains22@gmail.com',42,NULL,NULL),('Parent',50,_binary '\0','Petroje','Petrijevic','$2a$10$WAEVEvucRWXBtpKHDz6XSe5xtRr3KKeZ0FTcCNx76ixxqKb.EW/9m','petroje123',0,'lalalalalal@gmail.com',42,NULL,NULL),('Parent',51,_binary '\0','Vukoje','vukojevic','$2a$10$7ufnRdXrPnRQwlkQZnmwxe9qNysrjMPHGsmyWCdXC3wbdTX3M44GS','vukoje123',0,'lalalalalalaa@gmail.com',42,NULL,NULL),('Teacher',52,_binary '\0','Kurt','Cobain','$2a$10$yuYQRsM2ZPsDuGDNeiEh2eVsZvWaf0/rGjxaj5NADbDjdT4eE9Z0.','kurt23',0,NULL,45,NULL,NULL),('Teacher',53,_binary '\0','Jimi','Hendrix','$2a$10$oREj37A0IJpTQishgmGKrOv3fUIgMbSTJK9tYX2ufP/CWCYeVx5R2','jimi123',0,NULL,45,NULL,NULL),('Teacher',54,_binary '\0','Eric ','Clapton','$2a$10$bX41.P3CiDAk7rZvcbXb2ev.QhuTXQKaBpTtqM1fhWxlDchpD4TGS','eric123',0,NULL,45,NULL,NULL),('Teacher',55,_binary '\0','Layne','Staley','$2a$10$fSeo3NtmlZonnoID8/L8fu1COI2OxaG1Sesq3Yj8904wqK7pjLGle','layne123',0,NULL,45,NULL,NULL),('Student',56,_binary '\0','Johnny','Rotten','$2a$10$8w9/R1EAlO.7P1FIna1ucuEUcKMoHG5j9fhkvOaAdWA1bFy25PSai','johnny123',2,NULL,43,17,151),('Student',57,_binary '\0','Sid','Vicious','$2a$10$FiFIMGFONBL9FWBJg7O08upZwFEDtNwEbuIHQLU2MTZLtKX5ym33S','sid123',2,NULL,43,17,151),('Student',58,_binary '\0','Gwen','Stefani','$2a$10$k7KlA45MkAGMpFp2c2hhm.SeuhKOh1c0TlOYdckwV/lJ91ADQg70u','gwen123',2,NULL,43,16,51),('Student',59,_binary '\0','Roger','Waters','$2a$10$uC1dqmB0DWwlY5k2SpCB7uiPyyh6pCEvlZaeXPNlRruh9I/5bV4Bm','roger123',2,NULL,43,16,50),('Student',86,_binary '\0','Vlade','Divac','$2a$10$ixTLFP9.2S4ro62Yc749UurXPcO7PRAOEWXRylJgK97J7smPwXsMq','vlade123',2,NULL,43,16,49),('Student',87,_binary '\0','Milunka','Savic','$2a$10$zKEsYcVsCYW9iaXtgMSKf.yWnYX.eWKQyJ4b3HhNaQkhEQPPuZ0Me','milunka123',2,NULL,43,16,12),('Student',88,_binary '\0','Novak','Djokovic','$2a$10$PQe9VL9j6FDoRIWwffZmH.6d.GrJN22bQ.g2044QiPrkRswioq79S','novak123',2,NULL,43,16,49),('Teacher',89,_binary '\0','Jovanka','Orleanka','$2a$10$INP4bb99zl26uz/cvw5jye40IaBo759KerTOnUJrVdrx23TiMW2X.','jovanka123',0,NULL,45,NULL,NULL),('Teacher',90,_binary '\0','Crni','Djordje','$2a$10$ipsoP3fQldL9586bj2VVO.xlsfmMeUaVnQV7jx9xliic8St/ezePe','crni123',0,NULL,45,NULL,NULL),('Parent',151,_binary '\0','Nebo','Nebojsa','$2a$10$2htQ2J9ikfLOI9gHhhS9kOT1fA1FM0z9A1bsOjO6CCYTPJ5N.cLGm','nebo123',0,'horva.n@uns.ac.rs',42,NULL,NULL);
/*!40000 ALTER TABLE `user_entity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-10 14:57:19
