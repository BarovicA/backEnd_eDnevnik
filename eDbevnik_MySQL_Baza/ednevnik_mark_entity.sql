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
-- Table structure for table `mark_entity`
--

DROP TABLE IF EXISTS `mark_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mark_entity` (
  `id` bigint NOT NULL,
  `date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `mark_value` int DEFAULT NULL,
  `version` int DEFAULT NULL,
  `teacher_subject_student` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKscb4xa12ufguabx4wm7oipbnt` (`teacher_subject_student`),
  CONSTRAINT `FKscb4xa12ufguabx4wm7oipbnt` FOREIGN KEY (`teacher_subject_student`) REFERENCES `teacher_subject_student_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mark_entity`
--

LOCK TABLES `mark_entity` WRITE;
/*!40000 ALTER TABLE `mark_entity` DISABLE KEYS */;
INSERT INTO `mark_entity` VALUES (142,'2023-03-09',_binary '\0',0,0,104),(143,'2023-03-09',_binary '\0',0,0,119),(144,'2023-03-09',_binary '\0',0,0,124),(145,'2023-03-09',_binary '\0',4,0,109),(146,'2023-03-09',_binary '\0',4,0,114),(147,'2023-03-09',_binary '\0',4,0,78),(148,'2023-03-09',_binary '\0',4,0,78),(149,'2023-03-09',_binary '\0',4,0,78),(150,'2023-03-09',_binary '\0',2,1,106);
/*!40000 ALTER TABLE `mark_entity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-10 14:57:17
