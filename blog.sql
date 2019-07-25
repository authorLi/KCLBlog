-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: blog
-- ------------------------------------------------------
-- Server version	5.7.24

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
-- Table structure for table `blog_comment`
--

DROP TABLE IF EXISTS `blog_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_comment` (
  `blog_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_fk711og2oqkoc82slgnfws8t0` (`comment_id`),
  KEY `FKb9fyyxhsmiwujc51d7svj9ofi` (`blog_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_comment`
--

LOCK TABLES `blog_comment` WRITE;
/*!40000 ALTER TABLE `blog_comment` DISABLE KEYS */;
INSERT INTO `blog_comment` VALUES (15,10),(15,8),(23,11),(23,12),(24,13),(24,14),(32,16);
/*!40000 ALTER TABLE `blog_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kcl_blogs`
--

DROP TABLE IF EXISTS `kcl_blogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kcl_blogs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` int(11) DEFAULT NULL,
  `content` longtext NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `html_content` longtext NOT NULL,
  `likes` int(11) DEFAULT NULL,
  `summary` varchar(200) NOT NULL,
  `tags` varchar(100) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `viewed` int(11) DEFAULT NULL,
  `catalog_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd3c1rru9yjd3qw0l3c8vfgxni` (`catalog_id`),
  KEY `FKle4i8ropj05w86o2d622m8557` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kcl_blogs`
--

LOCK TABLES `kcl_blogs` WRITE;
/*!40000 ALTER TABLE `kcl_blogs` DISABLE KEYS */;
INSERT INTO `kcl_blogs` VALUES (24,2,'aaaaaaaaaaaaaaaaa','2019-07-24 14:34:22','aaaaaaaaaaaa',0,'aaaaaaaaaaaaaaaaa','aaaaaaaaaaaaaaaaa','aaa',0,1,2),(25,0,'bbbbbbbbbbbbb','2019-07-24 14:39:18','bbbbbbbbbbbbb',0,'bbbbbbbbbbbb','bbb','bbb',0,2,2),(26,0,'ccccccccccccccccccccc','2019-07-24 14:40:22','ccccccccccccccccccccc',0,'cccccccccccccccccc','ccc','ccc',0,3,2),(27,0,'ddddddddddddd','2019-07-24 14:40:38','ddddddddddddd',0,'dddddddddd','ddd','ddd',0,4,2),(28,0,'eeeeeeeeeeeeeee','2019-07-24 14:40:49','eeeeeeeeeeeeeee',0,'eeeeeeeeeee','eee','eee',0,5,2),(29,0,'fffffffffffffff','2019-07-24 14:40:58','fffffffffffffff',0,'ffffffffffffff','fff','fff',1,1,2),(30,0,'ggggggggggggggg','2019-07-24 14:41:08','ggggggggggggggg',0,'ggggggggggg','ggg','ggg',0,6,2),(31,0,'aaaaaaaaaaaaaaaaa','2019-07-25 02:40:34','aaaaaaaaaaaaa',0,'aaaaaaaaaaaaaaaaa','aaa,aaaaaaaaaaaaaaaaa','jjj',0,7,2),(32,1,'aaaaaaaaaaaaaa','2019-07-25 06:21:50','aaaaaaaaaaaaaa',2,'aaaaaaaaaaa','aaa','aaaaaaaaaaa',2,12,7);
/*!40000 ALTER TABLE `kcl_blogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kcl_catalogs`
--

DROP TABLE IF EXISTS `kcl_catalogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kcl_catalogs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcawdj6pvpuy0ampr8lhru4su2` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kcl_catalogs`
--

LOCK TABLES `kcl_catalogs` WRITE;
/*!40000 ALTER TABLE `kcl_catalogs` DISABLE KEYS */;
INSERT INTO `kcl_catalogs` VALUES (1,'Java',2),(2,'Python',2),(3,'JavaScript',2),(4,'MyBatis',2),(5,'BootStrap',2),(6,'.NET',2),(7,'Thymeleaf',2),(12,'Java',7);
/*!40000 ALTER TABLE `kcl_catalogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kcl_comments`
--

DROP TABLE IF EXISTS `kcl_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kcl_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL,
  `create_time` date NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa93uslfldws2arakpded1dii2` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kcl_comments`
--

LOCK TABLES `kcl_comments` WRITE;
/*!40000 ALTER TABLE `kcl_comments` DISABLE KEYS */;
INSERT INTO `kcl_comments` VALUES (10,'aaa','2019-07-24',2),(11,'aaa','2019-07-24',2),(12,'aaaaaa','2019-07-24',7),(13,'aaa','2019-07-24',2),(14,'aaa','2019-07-24',7),(16,'我写的挺好的','2019-07-25',7);
/*!40000 ALTER TABLE `kcl_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kcl_permissions`
--

DROP TABLE IF EXISTS `kcl_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kcl_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL,
  `resource_type` enum('menu','button') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kcl_permissions`
--

LOCK TABLES `kcl_permissions` WRITE;
/*!40000 ALTER TABLE `kcl_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `kcl_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kcl_roles`
--

DROP TABLE IF EXISTS `kcl_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kcl_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kcl_roles`
--

LOCK TABLES `kcl_roles` WRITE;
/*!40000 ALTER TABLE `kcl_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `kcl_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kcl_users`
--

DROP TABLE IF EXISTS `kcl_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kcl_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(200) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2c173entwkkfoke62m8td71b2` (`email`),
  UNIQUE KEY `UK_7u7qgh4j05624r0jy1s9kwof8` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kcl_users`
--

LOCK TABLES `kcl_users` WRITE;
/*!40000 ALTER TABLE `kcl_users` DISABLE KEYS */;
INSERT INTO `kcl_users` VALUES (1,'','anonymous','匿名用户','321af22500e8ae6151dbd552485d0d33','anonymous'),(2,'http://purp508wq.bkt.clouddn.com/91a4402b40ab491fb0d6f229a1d15d4f.jpg','mycclee@qq.com','李彦祖','321af22500e8ae6151dbd552485d0d33','mycclee'),(7,'http://purp508wq.bkt.clouddn.com/5bacee44d8e94e26ba47210727dc7ce4.jpg','123@qq.com','刘某','d177f909a96707f0f1d56b05f7ae9db7','dogflying');
/*!40000 ALTER TABLE `kcl_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_permissions` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  KEY `FK3o3fsmfaw628li08kqdsp5519` (`permission_id`),
  KEY `FK9n0kpxlqfgf3fjc2t8o9olne3` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_permissions`
--

LOCK TABLES `roles_permissions` WRITE;
/*!40000 ALTER TABLE `roles_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_users`
--

DROP TABLE IF EXISTS `roles_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_users` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `FKm9sn742qqqiluep7gdviumygv` (`user_id`),
  KEY `FKkfj84h9v2cnypp1wm7s2j9yqs` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_users`
--

LOCK TABLES `roles_users` WRITE;
/*!40000 ALTER TABLE `roles_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKp3ed8pp96y8y656j73ac6xxor` (`role_id`),
  KEY `FKmt3bq09uhro3785bhewrwx2d5` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-25 15:52:07
