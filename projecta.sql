-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: projecta
-- ------------------------------------------------------
-- Server version	5.5.47-0ubuntu0.14.04.1

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` char(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('projecta','프로젝트A','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63'),('projectd','프로젝트D','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignment` (
  `seekerId` char(11) NOT NULL,
  `offererId` varchar(32) NOT NULL,
  PRIMARY KEY (`seekerId`,`offererId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='배정';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juso`
--

DROP TABLE IF EXISTS `juso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `juso` (
  `id` int(11) NOT NULL,
  `sidoId` int(11) NOT NULL,
  `sidoName` varchar(45) NOT NULL,
  `sigunguId` int(11) NOT NULL,
  `sigunguName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sidoId`,`sigunguId`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juso`
--

LOCK TABLES `juso` WRITE;
/*!40000 ALTER TABLE `juso` DISABLE KEYS */;
INSERT INTO `juso` VALUES (188,11,'서울특별시',110,'종로구'),(189,11,'서울특별시',140,'중구'),(186,11,'서울특별시',170,'용산구'),(181,11,'서울특별시',200,'성동구'),(171,11,'서울특별시',215,'광진구'),(176,11,'서울특별시',230,'동대문구'),(190,11,'서울특별시',260,'중랑구'),(182,11,'서울특별시',290,'성북구'),(168,11,'서울특별시',305,'강북구'),(175,11,'서울특별시',320,'도봉구'),(174,11,'서울특별시',350,'노원구'),(187,11,'서울특별시',380,'은평구'),(179,11,'서울특별시',410,'서대문구'),(178,11,'서울특별시',440,'마포구'),(184,11,'서울특별시',470,'양천구'),(169,11,'서울특별시',500,'강서구'),(172,11,'서울특별시',530,'구로구'),(173,11,'서울특별시',545,'금천구'),(185,11,'서울특별시',560,'영등포구'),(177,11,'서울특별시',590,'동작구'),(170,11,'서울특별시',620,'관악구'),(180,11,'서울특별시',650,'서초구'),(166,11,'서울특별시',680,'강남구'),(183,11,'서울특별시',710,'송파구'),(167,11,'서울특별시',740,'강동구'),(138,26,'부산광역시',110,'중구'),(134,26,'부산광역시',140,'서구'),(128,26,'부산광역시',170,'동구'),(137,26,'부산광역시',200,'영도구'),(130,26,'부산광역시',230,'부산진구'),(129,26,'부산광역시',260,'동래구'),(127,26,'부산광역시',290,'남구'),(131,26,'부산광역시',320,'북구'),(139,26,'부산광역시',350,'해운대구'),(133,26,'부산광역시',380,'사하구'),(125,26,'부산광역시',410,'금정구'),(124,26,'부산광역시',440,'강서구'),(136,26,'부산광역시',470,'연제구'),(135,26,'부산광역시',500,'수영구'),(132,26,'부산광역시',530,'사상구'),(126,26,'부산광역시',710,'기장군'),(88,27,'대구광역시',110,'중구'),(84,27,'대구광역시',140,'동구'),(86,27,'대구광역시',170,'서구'),(81,27,'대구광역시',200,'남구'),(85,27,'대구광역시',230,'북구'),(87,27,'대구광역시',260,'수성구'),(82,27,'대구광역시',290,'달서구'),(83,27,'대구광역시',710,'달성군'),(104,28,'인천광역시',110,'중구'),(99,28,'인천광역시',140,'동구'),(97,28,'인천광역시',170,'남구'),(102,28,'인천광역시',185,'연수구'),(98,28,'인천광역시',200,'남동구'),(100,28,'인천광역시',237,'부평구'),(96,28,'인천광역시',245,'계양구'),(101,28,'인천광역시',260,'서구'),(95,28,'인천광역시',710,'강화군'),(103,28,'인천광역시',720,'옹진군'),(25,29,'광주광역시',110,'동구'),(27,29,'광주광역시',140,'서구'),(24,29,'광주광역시',155,'남구'),(26,29,'광주광역시',170,'북구'),(23,29,'광주광역시',200,'광산구'),(106,30,'대전광역시',110,'동구'),(109,30,'대전광역시',140,'중구'),(107,30,'대전광역시',170,'서구'),(108,30,'대전광역시',200,'유성구'),(105,30,'대전광역시',230,'대덕구'),(94,31,'울산광역시',110,'중구'),(90,31,'울산광역시',140,'남구'),(91,31,'울산광역시',170,'동구'),(92,31,'울산광역시',200,'북구'),(93,31,'울산광역시',710,'울주군'),(89,36,'세종특별자치시',110,NULL),(229,41,'경기도',111,'수원시 장안구'),(227,41,'경기도',113,'수원시 권선구'),(230,41,'경기도',115,'수원시 팔달구'),(228,41,'경기도',117,'수원시 영통구'),(225,41,'경기도',131,'성남시 수정구'),(226,41,'경기도',133,'성남시 중원구'),(224,41,'경기도',135,'성남시 분당구'),(246,41,'경기도',150,'의정부시'),(236,41,'경기도',171,'안양시 만안구'),(235,41,'경기도',173,'안양시 동안구'),(223,41,'경기도',195,'부천시 원미구'),(221,41,'경기도',197,'부천시 소사구'),(222,41,'경기도',199,'부천시 오정구'),(214,41,'경기도',210,'광명시'),(249,41,'경기도',220,'평택시'),(220,41,'경기도',250,'동두천시'),(233,41,'경기도',271,'안산시 상록구'),(232,41,'경기도',273,'안산시 단원구'),(210,41,'경기도',281,'고양시 덕양구'),(211,41,'경기도',285,'고양시 일산동구'),(212,41,'경기도',287,'고양시 일산서구'),(213,41,'경기도',290,'과천시'),(216,41,'경기도',310,'구리시'),(219,41,'경기도',360,'남양주시'),(241,41,'경기도',370,'오산시'),(231,41,'경기도',390,'시흥시'),(217,41,'경기도',410,'군포시'),(245,41,'경기도',430,'의왕시'),(251,41,'경기도',450,'하남시'),(244,41,'경기도',461,'용인시 처인구'),(242,41,'경기도',463,'용인시 기흥구'),(243,41,'경기도',465,'용인시 수지구'),(248,41,'경기도',480,'파주시'),(247,41,'경기도',500,'이천시'),(234,41,'경기도',550,'안성시'),(218,41,'경기도',570,'김포시'),(252,41,'경기도',590,'화성시'),(215,41,'경기도',610,'광주시'),(237,41,'경기도',630,'양주시'),(250,41,'경기도',650,'포천시'),(239,41,'경기도',670,'여주시'),(240,41,'경기도',800,'연천군'),(209,41,'경기도',820,'가평군'),(238,41,'경기도',830,'양평군'),(203,42,'강원도',110,'춘천시'),(199,42,'강원도',130,'원주시'),(191,42,'강원도',150,'강릉시'),(193,42,'강원도',170,'동해시'),(204,42,'강원도',190,'태백시'),(195,42,'강원도',210,'속초시'),(194,42,'강원도',230,'삼척시'),(206,42,'강원도',720,'홍천군'),(208,42,'강원도',730,'횡성군'),(198,42,'강원도',750,'영월군'),(205,42,'강원도',760,'평창군'),(201,42,'강원도',770,'정선군'),(202,42,'강원도',780,'철원군'),(207,42,'강원도',790,'화천군'),(196,42,'강원도',800,'양구군'),(200,42,'강원도',810,'인제군'),(192,42,'강원도',820,'고성군'),(197,42,'강원도',830,'양양군'),(119,43,'충청북도',111,'청주시 상당구'),(120,43,'충청북도',112,'청주시 서원구'),(122,43,'충청북도',113,'청주시 흥덕구'),(121,43,'충청북도',114,'청주시 청원구'),(123,43,'충청북도',130,'충주시'),(116,43,'충청북도',150,'제천시'),(112,43,'충청북도',720,'보은군'),(114,43,'충청북도',730,'옥천군'),(113,43,'충청북도',740,'영동군'),(117,43,'충청북도',745,'증평군'),(118,43,'충청북도',750,'진천군'),(110,43,'충청북도',760,'괴산군'),(115,43,'충청북도',770,'음성군'),(111,43,'충청북도',800,'단양군'),(39,44,'충청남도',131,'천안시 동남구'),(40,44,'충청남도',133,'천안시 서북구'),(29,44,'충청남도',150,'공주시'),(33,44,'충청남도',180,'보령시'),(37,44,'충청남도',200,'아산시'),(35,44,'충청남도',210,'서산시'),(31,44,'충청남도',230,'논산시'),(28,44,'충청남도',250,'계룡시'),(32,44,'충청남도',270,'당진시'),(30,44,'충청남도',710,'금산군'),(34,44,'충청남도',760,'부여군'),(36,44,'충청남도',770,'서천군'),(41,44,'충청남도',790,'청양군'),(43,44,'충청남도',800,'홍성군'),(38,44,'충청남도',810,'예산군'),(42,44,'충청남도',825,'태안군'),(78,45,'전라북도',111,'전주시 완산구'),(77,45,'전라북도',113,'전주시 덕진구'),(67,45,'전라북도',130,'군산시'),(74,45,'전라북도',140,'익산시'),(79,45,'전라북도',180,'정읍시'),(69,45,'전라북도',190,'남원시'),(68,45,'전라북도',210,'김제시'),(73,45,'전라북도',710,'완주군'),(80,45,'전라북도',720,'진안군'),(70,45,'전라북도',730,'무주군'),(76,45,'전라북도',740,'장수군'),(75,45,'전라북도',750,'임실군'),(72,45,'전라북도',770,'순창군'),(66,45,'전라북도',790,'고창군'),(71,45,'전라북도',800,'부안군'),(51,46,'전라남도',110,'목포시'),(56,46,'전라남도',130,'여수시'),(54,46,'전라남도',150,'순천시'),(49,46,'전라남도',170,'나주시'),(47,46,'전라남도',230,'광양시'),(50,46,'전라남도',710,'담양군'),(46,46,'전라남도',720,'곡성군'),(48,46,'전라남도',730,'구례군'),(45,46,'전라남도',770,'고흥군'),(53,46,'전라남도',780,'보성군'),(65,46,'전라남도',790,'화순군'),(61,46,'전라남도',800,'장흥군'),(44,46,'전라남도',810,'강진군'),(64,46,'전라남도',820,'해남군'),(58,46,'전라남도',830,'영암군'),(52,46,'전라남도',840,'무안군'),(63,46,'전라남도',860,'함평군'),(57,46,'전라남도',870,'영광군'),(60,46,'전라남도',880,'장성군'),(59,46,'전라남도',890,'완도군'),(62,46,'전라남도',900,'진도군'),(55,46,'전라남도',910,'신안군'),(164,47,'경상북도',111,'포항시 남구'),(165,47,'경상북도',113,'포항시 북구'),(143,47,'경상북도',130,'경주시'),(147,47,'경상북도',150,'김천시'),(152,47,'경상북도',170,'안동시'),(145,47,'경상북도',190,'구미시'),(155,47,'경상북도',210,'영주시'),(156,47,'경상북도',230,'영천시'),(150,47,'경상북도',250,'상주시'),(148,47,'경상북도',280,'문경시'),(142,47,'경상북도',290,'경산시'),(146,47,'경상북도',720,'군위군'),(160,47,'경상북도',730,'의성군'),(162,47,'경상북도',750,'청송군'),(154,47,'경상북도',760,'영양군'),(153,47,'경상북도',770,'영덕군'),(161,47,'경상북도',820,'청도군'),(144,47,'경상북도',830,'고령군'),(151,47,'경상북도',840,'성주군'),(163,47,'경상북도',850,'칠곡군'),(157,47,'경상북도',900,'예천군'),(149,47,'경상북도',920,'봉화군'),(159,47,'경상북도',930,'울진군'),(158,47,'경상북도',940,'울릉군'),(16,48,'경상남도',121,'창원시 의창구'),(15,48,'경상남도',123,'창원시 성산구'),(13,48,'경상남도',125,'창원시 마산합포구'),(14,48,'경상남도',127,'창원시 마산회원구'),(17,48,'경상남도',129,'창원시 진해구'),(11,48,'경상남도',170,'진주시'),(18,48,'경상남도',220,'통영시'),(7,48,'경상남도',240,'사천시'),(4,48,'경상남도',250,'김해시'),(6,48,'경상남도',270,'밀양시'),(1,48,'경상남도',310,'거제시'),(9,48,'경상남도',330,'양산시'),(10,48,'경상남도',720,'의령군'),(20,48,'경상남도',730,'함안군'),(12,48,'경상남도',740,'창녕군'),(3,48,'경상남도',820,'고성군'),(5,48,'경상남도',840,'남해군'),(19,48,'경상남도',850,'하동군'),(8,48,'경상남도',860,'산청군'),(21,48,'경상남도',870,'함양군'),(2,48,'경상남도',880,'거창군'),(22,48,'경상남도',890,'합천군'),(141,50,'제주특별자치도',110,'제주시'),(140,50,'제주특별자치도',130,'서귀포시');
/*!40000 ALTER TABLE `juso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offerer`
--

DROP TABLE IF EXISTS `offerer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offerer` (
  `id` varchar(32) NOT NULL COMMENT '아이디',
  `password` char(64) NOT NULL COMMENT 'sha256',
  `name` varchar(32) NOT NULL COMMENT '상호',
  `offererName` varchar(64) NOT NULL COMMENT '상호',
  `registerDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '회원가입(등록) 날짜',
  `level` enum('BASIC','PREMIUM') DEFAULT 'BASIC',
  `level_modifyDate` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `addrZipcode` varchar(6) DEFAULT NULL COMMENT '우편번호',
  `region` int(10) unsigned DEFAULT NULL,
  `addrFull` varchar(60) DEFAULT NULL,
  `offererNumber` char(10) NOT NULL,
  `offererBrief` varchar(64) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL COMMENT '연락처',
  `cellPhone` varchar(11) DEFAULT NULL COMMENT 'SMS 수신번호',
  `businessType` int(10) unsigned DEFAULT NULL,
  `payDate` date DEFAULT NULL COMMENT '유료 결제 날짜',
  `eosDate` date DEFAULT NULL COMMENT '유료 만료 날짜',
  `sidoId` int(10) unsigned NOT NULL,
  `sigunguId` int(10) unsigned NOT NULL,
  `postcode` varchar(12) DEFAULT NULL,
  `address1` varchar(128) NOT NULL,
  `address2` varchar(128) DEFAULT NULL,
  `mapFilename` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='업주';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offerer`
--

LOCK TABLES `offerer` WRITE;
/*!40000 ALTER TABLE `offerer` DISABLE KEYS */;
INSERT INTO `offerer` VALUES ('projectd','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63','qwe123','프로젝트D(주)','2016-02-16 06:03:08','BASIC','0000-00-00 00:00:00',NULL,NULL,NULL,'000000000',NULL,'021231234','01012341234',NULL,NULL,NULL,11,801,'06120','서울 강남구 논현동 202-16','3층 소프트피부과','/resources/offerer/projectd.png'),('test1','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63','qwe123','포레스트키친','2016-02-16 06:04:49','BASIC','0000-00-00 00:00:00',NULL,NULL,NULL,'0000000000',NULL,'029300707','0102332342',NULL,NULL,NULL,11,501,'01714','서울 노원구 중계동 359-14','','/resources/offerer/test1.png'),('test2','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63','qwe123','우주미','2016-02-16 06:15:21','BASIC','0000-00-00 00:00:00',NULL,NULL,NULL,'0000000000',NULL,'023547788','0102342351',NULL,NULL,NULL,11,801,'03384','서울 은평구 녹번동 185','','/resources/offerer/test2.png'),('test3','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63','qwe123','새마을식당','2016-02-16 06:16:25','BASIC','0000-00-00 00:00:00',NULL,NULL,NULL,'00112222',NULL,'024761485','0102955632',NULL,NULL,NULL,11,401,'05355','서울 강동구 길동 449-1','','/resources/offerer/test3.png'),('test4','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63','qwe123','사당삼겹살','2016-02-16 06:17:51','BASIC','0000-00-00 00:00:00',NULL,NULL,NULL,'112222233',NULL,'025971981','0102349533',NULL,NULL,NULL,11,201,'08807','서울 관악구 남현동 1061-13','','/resources/offerer/test4.png'),('test5','18138372fad4b94533cd4881f03dc6c69296dd897234e0cee83f727e2e6b1f63','qwe123','육회자매집','2016-02-16 06:18:35','BASIC','0000-00-00 00:00:00',NULL,NULL,NULL,'1122333',NULL,'0222748344','0106434576',NULL,NULL,NULL,11,101,'03195','서울 종로구 종로4가 177','','/resources/offerer/test5.png');
/*!40000 ALTER TABLE `offerer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `offererId` varchar(32) NOT NULL COMMENT '업주 id',
  `workDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '업무 날짜',
  `workTime` tinyint(3) unsigned NOT NULL COMMENT '업무 시간',
  `ageRange` tinyint(4) DEFAULT '40',
  `workAbility` enum('SERVING','KITCHEN','ASSIST') NOT NULL DEFAULT 'SERVING' COMMENT '배정 업무',
  `matchStatus` enum('COMPLETION','INCOMPLETION') NOT NULL DEFAULT 'INCOMPLETION',
  `nation` enum('KOR','CHA','ETC') NOT NULL DEFAULT 'KOR',
  `registerDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `offererId` (`offererId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
INSERT INTO `requirement` VALUES (1,'test1','2016-02-28 19:00:00',5,40,'KITCHEN','INCOMPLETION','CHA','2016-02-16 09:26:01','MALE');
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seeker`
--

DROP TABLE IF EXISTS `seeker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seeker` (
  `id` char(11) NOT NULL COMMENT '전화번호 000-000-0000',
  `name` varchar(32) NOT NULL COMMENT '구직자 이름',
  `registerDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입날짜',
  `withdrawDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '탈퇴날짜',
  `birth` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `gender` enum('FEMALE','MALE') NOT NULL COMMENT '성별',
  `nation` enum('KOR','CHA','ETC') NOT NULL DEFAULT 'KOR',
  `region1` int(10) unsigned NOT NULL COMMENT '업무가능지역1',
  `region2` int(10) unsigned DEFAULT NULL COMMENT '업무가능지역2',
  `region3` int(10) unsigned DEFAULT NULL COMMENT '업무가능지역3',
  `workMday` char(7) NOT NULL COMMENT '업무가능 요일 (0110000 = 월화, 일월화수목금토)',
  `workQtime` char(4) NOT NULL,
  `licenseFile` varchar(256) DEFAULT NULL,
  `workAbility` enum('SERVING','KITCHEN','ASSIST') NOT NULL DEFAULT 'SERVING',
  `payDate` timestamp NULL DEFAULT NULL,
  `eosDate` timestamp NULL DEFAULT NULL,
  `cancelCount` int(10) unsigned DEFAULT '0',
  `credibility` tinyint(3) unsigned DEFAULT '0' COMMENT '신뢰도 (100)',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='구직자';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seeker`
--

LOCK TABLES `seeker` WRITE;
/*!40000 ALTER TABLE `seeker` DISABLE KEYS */;
INSERT INTO `seeker` VALUES ('01048597700','고영준','2016-02-16 06:01:32','0000-00-00 00:00:00','1980-12-31 15:00:00','MALE','CHA',175,174,168,'127','15',NULL,'KITCHEN',NULL,NULL,0,0),('01064179605','류용수','2016-02-16 06:00:06','0000-00-00 00:00:00','1983-12-31 15:00:00','MALE','CHA',190,189,187,'127','3',NULL,'KITCHEN',NULL,NULL,0,0),('01072390421','강석주','2016-02-16 05:58:56','0000-00-00 00:00:00','2009-12-31 15:00:00','MALE','CHA',166,167,180,'63','15',NULL,'SERVING',NULL,NULL,0,0);
/*!40000 ALTER TABLE `seeker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-16 21:13:16
