-- MySQL dump 10.13  Distrib 5.6.24, for linux-glibc2.5 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	5.6.19-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Admin` (
  `idAdmin` int(11) NOT NULL,
  `Username` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Password` longtext CHARACTER SET greek NOT NULL,
  PRIMARY KEY (`idAdmin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES (1,'Admin','Σx# Φw4<Α?ΒΩ');
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AndroidClient`
--

DROP TABLE IF EXISTS `AndroidClient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AndroidClient` (
  `UsernameAndroid`varchar(45) NOT NULL,
  `Password` longtext CHARACTER SET greek NOT NULL,
  `email` varchar(45) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`UsernameAndroid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `Periodic_Job`
--

DROP TABLE IF EXISTS `Periodic_Job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Periodic_Job` (
  `idPeriodic_Job` int(11) NOT NULL,
  `Software_Agent_idSoftware_Agent` varchar(100) NOT NULL,
  `Job` longtext,
  `pediod` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPeriodic_Job`),
  KEY `fk_Periodic_Job_Software_Agent1_idx` (`Software_Agent_idSoftware_Agent`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Periodic_Job`
--

LOCK TABLES `Periodic_Job` WRITE;
/*!40000 ALTER TABLE `Periodic_Job` DISABLE KEYS */;
/*!40000 ALTER TABLE `Periodic_Job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Result`
--

DROP TABLE IF EXISTS `Result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Result` (
  `idResult` int(11) NOT NULL,
  `id_command` varchar(45) NOT NULL,
  `Address` longtext NOT NULL,
  `Status` longtext NOT NULL,
  `Hostname` longtext NOT NULL,
  `Port` longtext NOT NULL,
  `Uptime` longtext NOT NULL,
  `Insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Software_Agent_idSoftware_Agent` varchar(100) NOT NULL,
  PRIMARY KEY (`idResult`),
  KEY `fk_Result_Software_Agent_idx` (`Software_Agent_idSoftware_Agent`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Result`
--

LOCK TABLES `Result` WRITE;
/*!40000 ALTER TABLE `Result` DISABLE KEYS */;
/*!40000 ALTER TABLE `Result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Software_Agent`
--

DROP TABLE IF EXISTS `Software_Agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Software_Agent` (
  `idSoftware_Agent` varchar(100) NOT NULL,
  `DeviceName` longtext NOT NULL,
  `IP` longtext NOT NULL,
  `MacAddr` longtext NOT NULL,
  `OsVersion` longtext NOT NULL,
  `NmapVersion` varchar(45) NOT NULL,
  `Status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idSoftware_Agent`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Software_Agent`
--

LOCK TABLES `Software_Agent` WRITE;
/*!40000 ALTER TABLE `Software_Agent` DISABLE KEYS */;
INSERT INTO `Software_Agent` VALUES ('root00-0C-29-38-01-F8123.2133.19.0-25-generic6.40','root','123.213','00-0C-29-38-01-F8','3.19.0-25-generic','6.40',1);
/*!40000 ALTER TABLE `Software_Agent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-15  9:11:50
