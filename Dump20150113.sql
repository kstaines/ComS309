CREATE DATABASE  IF NOT EXISTS `db309W03` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `db309W03`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: mysql.cs.iastate.edu    Database: db309W03
-- ------------------------------------------------------
-- Server version	5.1.73

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
-- Table structure for table `checkin`
--

DROP TABLE IF EXISTS `checkin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkin` (
  `checkinid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Check in ID',
  `studentid` bigint(20) NOT NULL COMMENT 'Student user ID checked in',
  `courseid` bigint(20) NOT NULL COMMENT 'Course ID checked in to',
  `latitude` float NOT NULL COMMENT 'Latitude Location info',
  `longitude` float NOT NULL COMMENT 'Longitude Location info',
  `ts_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of last update',
  PRIMARY KEY (`checkinid`),
  UNIQUE KEY `UNIQUE` (`studentid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='Table to store checkin information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin`
--

LOCK TABLES `checkin` WRITE;
/*!40000 ALTER TABLE `checkin` DISABLE KEYS */;
INSERT INTO `checkin` (`checkinid`, `studentid`, `courseid`, `latitude`, `longitude`, `ts_update`) VALUES (1,1,111,0,0,'2014-12-08 16:15:30');
/*!40000 ALTER TABLE `checkin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `courseid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID of course',
  `name` varchar(50) NOT NULL COMMENT 'Name of course.',
  `section` char(2) DEFAULT '00' COMMENT 'Section of Course',
  `location` varchar(50) NOT NULL COMMENT 'Location of course.',
  `ts_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of last update.',
  `time` time NOT NULL DEFAULT '00:00:00' COMMENT 'Time course scheduled.',
  `days` varchar(5) NOT NULL COMMENT 'Days course scheduled.',
  PRIMARY KEY (`courseid`)
) ENGINE=MyISAM AUTO_INCREMENT=139 DEFAULT CHARSET=latin1 COMMENT='List of courses with time and location.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` (`courseid`, `name`, `section`, `location`, `ts_update`, `time`, `days`) VALUES (92,'testcourse2','A','Coover','2014-12-04 15:59:09','11:00:00','MTF'),(88,'testcourse1','A','Coover','2014-12-04 15:37:14','13:00:00','MWF'),(111,'testcourse3','B','Gilman','2014-12-07 18:10:26','13:10:00','MTRF'),(138,'testcourse4','1','Coover','2014-12-09 00:28:10','13:00:00','MWF');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `studentid` bigint(20) NOT NULL COMMENT 'Student userid',
  `friendid` bigint(20) NOT NULL COMMENT 'Friend userid',
  `approved` char(1) NOT NULL DEFAULT 'N' COMMENT 'Friendship approved: Y. Not approved: N.',
  `ts_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp last updated'
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Correlation between a student and friends.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` (`studentid`, `friendid`, `approved`, `ts_update`) VALUES (633,1,'Y','2014-12-09 00:18:13'),(1,252,'Y','2014-11-17 15:40:16'),(1,264,'N','2014-11-17 15:59:22'),(1,263,'N','2014-11-17 15:48:26'),(1,133,'N','2014-11-17 15:47:48');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructorcourses`
--

DROP TABLE IF EXISTS `instructorcourses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instructorcourses` (
  `instructorid` bigint(20) NOT NULL COMMENT 'Instructor user ID',
  `courseid` bigint(20) NOT NULL COMMENT 'Course ID',
  `ts_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Correlation table between an instructor and their classes.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructorcourses`
--

LOCK TABLES `instructorcourses` WRITE;
/*!40000 ALTER TABLE `instructorcourses` DISABLE KEYS */;
INSERT INTO `instructorcourses` (`instructorid`, `courseid`, `ts_update`) VALUES (256,111,'2014-12-07 18:10:26'),(634,136,'2014-12-09 00:21:54');
/*!40000 ALTER TABLE `instructorcourses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionid` bigint(20) NOT NULL,
  PRIMARY KEY (`questionid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Questions that the Instructor posts.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentcourses`
--

DROP TABLE IF EXISTS `studentcourses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentcourses` (
  `studentid` bigint(20) NOT NULL COMMENT 'Student ID',
  `courseid` bigint(20) NOT NULL COMMENT 'Course ID',
  `points` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Points student has in course',
  `times_chk_in` bigint(20) DEFAULT '0' COMMENT 'Number of times checked into class.',
  `ts_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp last updated'
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Correlation between the courses and students.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentcourses`
--

LOCK TABLES `studentcourses` WRITE;
/*!40000 ALTER TABLE `studentcourses` DISABLE KEYS */;
INSERT INTO `studentcourses` (`studentid`, `courseid`, `points`, `times_chk_in`, `ts_update`) VALUES (1,88,7,7,'2014-12-09 00:06:42'),(1,92,4,4,'2014-12-08 16:03:55'),(1,111,2,2,'2014-12-08 16:34:14'),(2,111,1,1,'2014-12-08 16:14:30'),(633,88,0,0,'2014-12-09 00:18:59'),(633,92,0,0,'2014-12-09 00:19:09');
/*!40000 ALTER TABLE `studentcourses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary key of user table.',
  `username` varchar(50) NOT NULL COMMENT 'Username of the account',
  `password` varchar(50) NOT NULL COMMENT 'User password for account',
  `ts_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last updated or created timestamp',
  `userType` varchar(50) DEFAULT 'STUDENT' COMMENT 'Type of user account.',
  `totalPts` int(10) NOT NULL DEFAULT '0' COMMENT 'Total points of a user.',
  `approved` char(1) NOT NULL DEFAULT 'N' COMMENT 'Account approval status. Default to N (No).',
  PRIMARY KEY (`userid`),
  UNIQUE KEY `UNIQUE USER` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=635 DEFAULT CHARSET=latin1 COMMENT='Table to store all of the user accounts with their passwords';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`userid`, `username`, `password`, `ts_update`, `userType`, `totalPts`, `approved`) VALUES (1,'testuser','pass','2014-12-09 00:06:42','STUDENT',13,'Y'),(2,'testuser2','pass2','2014-12-08 16:14:30','STUDENT',1,'Y'),(133,'testuser3','pass3','2014-10-23 15:43:03','STUDENT',0,'Y'),(256,'testinstructor','pass','2014-10-30 15:24:53','INSTRUCTOR',0,'Y'),(264,'testuser6','pass','2014-10-24 15:40:32','STUDENT',0,'Y'),(632,'testinstructor4','pass','2014-12-09 00:27:20','INSTRUCTOR',0,'Y'),(272,'testuser8','pass','2014-12-08 16:36:47','INSTRUCTOR',0,'Y'),(274,'testadmin','pass','2014-10-30 15:22:13','ADMIN',0,'Y'),(633,'testuser9','pass','2014-12-09 00:16:39','STUDENT',0,'Y'),(634,'testinstructor5','pass','2014-12-09 00:20:14','INSTRUCTOR',0,'Y');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db309W03'
--

--
-- Dumping routines for database 'db309W03'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-13 22:07:22
