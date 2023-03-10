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
-- Table structure for table `teacher_subject_grade`
--

DROP TABLE IF EXISTS `teacher_subject_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_subject_grade` (
  `id` bigint NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `teacher_subject` bigint DEFAULT NULL,
  `grade` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6aa94443lhmdqb8m395qfm23t` (`teacher_subject`),
  KEY `FKrt4gb4d017ljcpl4201o5c6ys` (`grade`),
  CONSTRAINT `FK6aa94443lhmdqb8m395qfm23t` FOREIGN KEY (`teacher_subject`) REFERENCES `teacher_subject` (`id`),
  CONSTRAINT `FKrt4gb4d017ljcpl4201o5c6ys` FOREIGN KEY (`grade`) REFERENCES `grade_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_subject_grade`
--

LOCK TABLES `teacher_subject_grade` WRITE;
/*!40000 ALTER TABLE `teacher_subject_grade` DISABLE KEYS */;
INSERT INTO `teacher_subject_grade` VALUES (71,_binary '\0',0,61,17),(72,_binary '\0',0,62,17),(73,_binary '\0',0,63,17),(74,_binary '\0',0,64,17),(75,_binary '\0',0,65,17),(96,_binary '\0',0,61,16),(97,_binary '\0',0,62,16),(98,_binary '\0',0,93,16),(99,_binary '\0',0,94,16),(100,_binary '\0',0,95,16);
/*!40000 ALTER TABLE `teacher_subject_grade` ENABLE KEYS */;
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
