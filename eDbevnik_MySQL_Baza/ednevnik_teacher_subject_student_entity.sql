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
-- Table structure for table `teacher_subject_student_entity`
--

DROP TABLE IF EXISTS `teacher_subject_student_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_subject_student_entity` (
  `id` bigint NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `student` bigint DEFAULT NULL,
  `teacher_subject` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9d6l2bslonlhh3bqiisxnd8wm` (`student`),
  KEY `FK8sl3calndcho1vv7cq432jr3v` (`teacher_subject`),
  CONSTRAINT `FK8sl3calndcho1vv7cq432jr3v` FOREIGN KEY (`teacher_subject`) REFERENCES `teacher_subject` (`id`),
  CONSTRAINT `FK9d6l2bslonlhh3bqiisxnd8wm` FOREIGN KEY (`student`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_subject_student_entity`
--

LOCK TABLES `teacher_subject_student_entity` WRITE;
/*!40000 ALTER TABLE `teacher_subject_student_entity` DISABLE KEYS */;
INSERT INTO `teacher_subject_student_entity` VALUES (76,_binary '\0',0,9,61),(77,_binary '\0',0,9,62),(78,_binary '\0',0,9,63),(79,_binary '\0',0,9,64),(80,_binary '\0',0,9,65),(81,_binary '\0',0,10,61),(82,_binary '\0',0,10,62),(83,_binary '\0',0,10,63),(84,_binary '\0',0,10,64),(85,_binary '\0',0,10,65),(101,_binary '\0',0,86,61),(102,_binary '\0',0,86,62),(103,_binary '\0',0,86,93),(104,_binary '\0',0,86,94),(105,_binary '\0',0,86,95),(106,_binary '\0',0,87,61),(107,_binary '\0',0,87,62),(108,_binary '\0',0,87,93),(109,_binary '\0',0,87,94),(110,_binary '\0',0,87,95),(111,_binary '\0',0,88,61),(112,_binary '\0',0,88,62),(113,_binary '\0',0,88,93),(114,_binary '\0',0,88,94),(115,_binary '\0',0,88,95),(116,_binary '\0',0,58,61),(117,_binary '\0',0,58,62),(118,_binary '\0',0,58,93),(119,_binary '\0',0,58,94),(120,_binary '\0',0,58,95),(121,_binary '\0',0,59,61),(122,_binary '\0',0,59,62),(123,_binary '\0',0,59,93),(124,_binary '\0',0,59,94),(125,_binary '\0',0,59,95),(126,_binary '\0',0,11,61),(127,_binary '\0',0,11,62),(128,_binary '\0',0,11,63),(129,_binary '\0',0,11,64),(130,_binary '\0',0,11,65),(131,_binary '\0',0,56,61),(132,_binary '\0',0,56,62),(133,_binary '\0',0,56,63),(134,_binary '\0',0,56,64),(135,_binary '\0',0,56,65),(136,_binary '\0',0,57,61),(137,_binary '\0',0,57,62),(138,_binary '\0',0,57,63),(139,_binary '\0',0,57,64),(140,_binary '\0',0,57,65);
/*!40000 ALTER TABLE `teacher_subject_student_entity` ENABLE KEYS */;
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
