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
-- Table structure for table `grade_entity`
--

DROP TABLE IF EXISTS `grade_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grade_entity` (
  `id` bigint NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `school_year` varchar(255) DEFAULT NULL,
  `unit` int DEFAULT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgp60hwj1w6lf2j8jkkw04xiyb` (`school_year`,`unit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade_entity`
--

LOCK TABLES `grade_entity` WRITE;
/*!40000 ALTER TABLE `grade_entity` DISABLE KEYS */;
INSERT INTO `grade_entity` VALUES (14,_binary '\0','I',1,0),(15,_binary '\0','I',2,0),(16,_binary '\0','II',2,0),(17,_binary '\0','II',1,5),(18,_binary '\0','III',1,0),(19,_binary '\0','III',2,0),(20,_binary '\0','IV',2,0),(21,_binary '\0','IV',1,0),(22,_binary '\0','V',1,0),(24,_binary '\0','V',2,0),(25,_binary '\0','VI',1,0),(26,_binary '\0','VI',2,0),(27,_binary '\0','VII',1,0),(28,_binary '\0','VII',2,0),(29,_binary '\0','VIII',1,0),(30,_binary '\0','VIII',2,0),(92,_binary '\0','II',3,0),(152,_binary '\0','VI',3,0);
/*!40000 ALTER TABLE `grade_entity` ENABLE KEYS */;
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
