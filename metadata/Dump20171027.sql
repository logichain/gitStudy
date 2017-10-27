CREATE DATABASE  IF NOT EXISTS `testcasemanagedb` /*!40100 DEFAULT CHARACTER SET gbk */;
USE `testcasemanagedb`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: testcasemanagedb
-- ------------------------------------------------------
-- Server version	5.7.4-m14

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
-- Table structure for table `bug_type`
--

DROP TABLE IF EXISTS `bug_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bug_type` (
  `bt_id` int(11) NOT NULL AUTO_INCREMENT,
  `bt_name` varchar(45) DEFAULT NULL,
  `bt_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`bt_id`),
  UNIQUE KEY `bt_id_UNIQUE` (`bt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug_type`
--

LOCK TABLES `bug_type` WRITE;
/*!40000 ALTER TABLE `bug_type` DISABLE KEYS */;
INSERT INTO `bug_type` VALUES (1,'仕样方面',0),(2,'代码方面',0),(3,'性能方面',0),(4,'兼容性方面',0);
/*!40000 ALTER TABLE `bug_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case_attachment`
--

DROP TABLE IF EXISTS `case_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_attachment` (
  `ca_id` int(11) NOT NULL AUTO_INCREMENT,
  `ca_test_case` int(11) NOT NULL,
  `ca_code` varchar(45) NOT NULL,
  `ca_name` varchar(100) NOT NULL,
  `ca_url` varchar(100) NOT NULL,
  `ca_flag` int(11) NOT NULL,
  `ca_create_user` int(11) NOT NULL,
  `ca_create_time` datetime NOT NULL,
  `ca_local_url` varchar(100) DEFAULT NULL,
  `ca_submit_date` date DEFAULT NULL,
  `ca_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ca_id`),
  UNIQUE KEY `ca_id_UNIQUE` (`ca_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case_attachment`
--

LOCK TABLES `case_attachment` WRITE;
/*!40000 ALTER TABLE `case_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case_status`
--

DROP TABLE IF EXISTS `case_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_status` (
  `cs_id` int(11) NOT NULL AUTO_INCREMENT,
  `cs_name` varchar(45) DEFAULT NULL,
  `cs_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`cs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case_status`
--

LOCK TABLES `case_status` WRITE;
/*!40000 ALTER TABLE `case_status` DISABLE KEYS */;
INSERT INTO `case_status` VALUES (-1,'删除',0),(1,'待测试',0),(2,'关闭',0),(3,'已测试',0),(4,'修正',0);
/*!40000 ALTER TABLE `case_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case_type`
--

DROP TABLE IF EXISTS `case_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_type` (
  `ct_id` int(11) NOT NULL AUTO_INCREMENT,
  `ct_name` varchar(45) DEFAULT NULL,
  `ct_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`ct_id`),
  UNIQUE KEY `ct_id_UNIQUE` (`ct_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case_type`
--

LOCK TABLES `case_type` WRITE;
/*!40000 ALTER TABLE `case_type` DISABLE KEYS */;
INSERT INTO `case_type` VALUES (1,'标准',0),(2,'扩展',0),(3,'必要',0);
/*!40000 ALTER TABLE `case_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case_version_reference`
--

DROP TABLE IF EXISTS `case_version_reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_version_reference` (
  `cvr_id` int(11) NOT NULL AUTO_INCREMENT,
  `cvr_test_case` int(11) NOT NULL,
  `cvr_project_version` int(11) NOT NULL,
  `cvr_flag` int(11) NOT NULL DEFAULT '0',
  `cvr_case_output` varchar(200) DEFAULT NULL,
  `cvr_case_result` int(11) DEFAULT NULL,
  `cvr_case_status` int(11) DEFAULT NULL,
  `cvr_bug_type` int(11) DEFAULT NULL,
  `cvr_important_level` int(11) DEFAULT NULL,
  `cvr_test_user` int(11) DEFAULT NULL,
  `cvr_test_time` datetime DEFAULT NULL,
  `cvr_correct_user` int(11) DEFAULT NULL,
  `cvr_correct_time` datetime DEFAULT NULL,
  `cvr_close_user` int(11) DEFAULT NULL,
  `cvr_close_time` datetime DEFAULT NULL,
  PRIMARY KEY (`cvr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=344 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case_version_reference`
--

LOCK TABLES `case_version_reference` WRITE;
/*!40000 ALTER TABLE `case_version_reference` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_version_reference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cvr_attachment`
--

DROP TABLE IF EXISTS `cvr_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cvr_attachment` (
  `ca_id` int(11) NOT NULL AUTO_INCREMENT,
  `ca_case_version_reference` int(11) NOT NULL,
  `ca_code` varchar(45) NOT NULL,
  `ca_name` varchar(100) NOT NULL,
  `ca_url` varchar(100) NOT NULL,
  `ca_flag` int(11) NOT NULL,
  `ca_create_user` int(11) NOT NULL,
  `ca_create_time` datetime NOT NULL,
  `ca_local_url` varchar(100) DEFAULT NULL,
  `ca_submit_date` date DEFAULT NULL,
  `ca_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ca_id`),
  UNIQUE KEY `ca_id_UNIQUE` (`ca_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cvr_attachment`
--

LOCK TABLES `cvr_attachment` WRITE;
/*!40000 ALTER TABLE `cvr_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `cvr_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `d_id` int(11) NOT NULL AUTO_INCREMENT,
  `d_name` varchar(45) NOT NULL,
  `d_flag` int(11) NOT NULL,
  PRIMARY KEY (`d_id`),
  UNIQUE KEY `d_id_UNIQUE` (`d_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'开发部',0),(2,'品质部',1);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ico_menu`
--

DROP TABLE IF EXISTS `ico_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ico_menu` (
  `id` varchar(32) NOT NULL,
  `pid` varchar(32) NOT NULL,
  `menu_name` varchar(32) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `iconopen` varchar(255) DEFAULT NULL,
  `opened` varchar(1) DEFAULT NULL,
  `SORT_FLAG` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ico_menu`
--

LOCK TABLES `ico_menu` WRITE;
/*!40000 ALTER TABLE `ico_menu` DISABLE KEYS */;
INSERT INTO `ico_menu` VALUES ('0','-1','系统菜单','javascript: void(0);',NULL,NULL,NULL,NULL,NULL,1),('1','0','用例管理','javascript: void(0);',NULL,NULL,NULL,NULL,NULL,1),('2','0','项目管理','javascript: void(0);','1',NULL,NULL,NULL,NULL,2),('3','0','测试统计','javascript: void(0);',NULL,NULL,NULL,NULL,NULL,3),('4','2','项目管理','projectmanage.do?method=resetSearchProject','1',NULL,NULL,NULL,NULL,2),('5','2','新建项目','projectmanage.do?method=createProject','1',NULL,NULL,NULL,NULL,1),('6','0','式样书管理',NULL,NULL,NULL,NULL,NULL,NULL,5),('7','0','成绩书管理',NULL,NULL,NULL,NULL,NULL,NULL,6),('8','0','用例版本关联',NULL,NULL,NULL,NULL,NULL,NULL,4),('9','2','项目总计','teststatistics.do?method=projectDataStatistics','1',NULL,NULL,NULL,NULL,3),('99','0','系统设置','javascript: void(0);',NULL,NULL,NULL,NULL,NULL,9990),('9901','99','员工管理','accountManager.do?method=resetSearchAccount','1',NULL,NULL,NULL,NULL,9903),('9902','99','权限设置','accountSecurityManager.do?method=searchAccount','1',NULL,NULL,NULL,NULL,9902),('9904','99','修改密码','accountManager.do?method=load4ChangePWD',NULL,NULL,NULL,NULL,NULL,9904);
/*!40000 ALTER TABLE `ico_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `important_level`
--

DROP TABLE IF EXISTS `important_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `important_level` (
  `il_id` int(11) NOT NULL AUTO_INCREMENT,
  `il_name` varchar(45) DEFAULT NULL,
  `il_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`il_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `important_level`
--

LOCK TABLES `important_level` WRITE;
/*!40000 ALTER TABLE `important_level` DISABLE KEYS */;
INSERT INTO `important_level` VALUES (1,'致命',0),(2,'重要',0),(3,'一般',0);
/*!40000 ALTER TABLE `important_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_function_reference`
--

DROP TABLE IF EXISTS `member_function_reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member_function_reference` (
  `mfr_id` int(11) NOT NULL AUTO_INCREMENT,
  `mfr_team_member` int(11) DEFAULT NULL,
  `mfr_module_function` int(11) DEFAULT NULL,
  `mfr_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`mfr_id`),
  UNIQUE KEY `mf_id_UNIQUE` (`mfr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_function_reference`
--

LOCK TABLES `member_function_reference` WRITE;
/*!40000 ALTER TABLE `member_function_reference` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_function_reference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_function`
--

DROP TABLE IF EXISTS `module_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_function` (
  `mu_id` int(11) NOT NULL AUTO_INCREMENT,
  `mu_module` int(11) DEFAULT NULL,
  `mu_name` varchar(45) DEFAULT NULL,
  `mu_remark` varchar(200) DEFAULT NULL,
  `mu_flag` int(11) DEFAULT NULL,
  `mu_parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`mu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_function`
--

LOCK TABLES `module_function` WRITE;
/*!40000 ALTER TABLE `module_function` DISABLE KEYS */;
/*!40000 ALTER TABLE `module_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(45) NOT NULL,
  `p_develop_leader` int(11) DEFAULT NULL,
  `p_test_leader` int(11) DEFAULT NULL,
  `p_develop_begin` date DEFAULT NULL,
  `p_develop_end` date DEFAULT NULL,
  `p_test_begin` date DEFAULT NULL,
  `p_test_end` date DEFAULT NULL,
  `p_remark` varchar(200) DEFAULT NULL,
  `p_flag` int(11) DEFAULT NULL,
  `p_create_user` int(11) DEFAULT NULL,
  `p_create_time` datetime DEFAULT NULL,
  `p_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`p_id`),
  UNIQUE KEY `p_id_UNIQUE` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_attachment`
--

DROP TABLE IF EXISTS `project_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_attachment` (
  `pa_id` int(11) NOT NULL AUTO_INCREMENT,
  `pa_project_version` int(11) NOT NULL,
  `pa_code` varchar(45) NOT NULL,
  `pa_name` varchar(100) NOT NULL,
  `pa_url` varchar(100) NOT NULL,
  `pa_flag` int(11) NOT NULL,
  `pa_create_user` int(11) NOT NULL,
  `pa_create_time` datetime NOT NULL,
  `pa_local_url` varchar(100) DEFAULT NULL,
  `pa_submit_date` date DEFAULT NULL,
  `pa_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pa_id`),
  UNIQUE KEY `pa_id_UNIQUE` (`pa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_attachment`
--

LOCK TABLES `project_attachment` WRITE;
/*!40000 ALTER TABLE `project_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_module`
--

DROP TABLE IF EXISTS `project_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_module` (
  `pm_id` int(11) NOT NULL AUTO_INCREMENT,
  `pm_project` int(11) DEFAULT NULL,
  `pm_name` varchar(45) DEFAULT NULL,
  `pm_remark` varchar(200) DEFAULT NULL,
  `pm_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`pm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_module`
--

LOCK TABLES `project_module` WRITE;
/*!40000 ALTER TABLE `project_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_version`
--

DROP TABLE IF EXISTS `project_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_version` (
  `pv_id` int(11) NOT NULL AUTO_INCREMENT,
  `pv_project` int(11) NOT NULL,
  `pv_version` varchar(45) NOT NULL,
  `pv_develop_leader` int(11) DEFAULT NULL,
  `pv_test_leader` int(11) DEFAULT NULL,
  `pv_develop_begin` date DEFAULT NULL,
  `pv_develop_end` date DEFAULT NULL,
  `pv_test_begin` date DEFAULT NULL,
  `pv_test_end` date DEFAULT NULL,
  `pv_flag` int(11) NOT NULL,
  `pv_remark` varchar(200) DEFAULT NULL,
  `pv_create_user` int(11) DEFAULT NULL,
  `pv_create_time` datetime DEFAULT NULL,
  `pv_init` int(11) DEFAULT NULL,
  PRIMARY KEY (`pv_id`),
  UNIQUE KEY `pv_id_UNIQUE` (`pv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_version`
--

LOCK TABLES `project_version` WRITE;
/*!40000 ALTER TABLE `project_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_member`
--

DROP TABLE IF EXISTS `team_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_member` (
  `tm_id` int(11) NOT NULL AUTO_INCREMENT,
  `tm_project` int(11) DEFAULT NULL,
  `tm_account` int(11) DEFAULT NULL,
  `tm_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`tm_id`),
  UNIQUE KEY `tm_id_UNIQUE` (`tm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_member`
--

LOCK TABLES `team_member` WRITE;
/*!40000 ALTER TABLE `team_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_case`
--

DROP TABLE IF EXISTS `test_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_case` (
  `tc_id` int(11) NOT NULL AUTO_INCREMENT,
  `tc_module_function` int(11) DEFAULT NULL,
  `tc_code` varchar(45) DEFAULT NULL,
  `tc_test_objective` varchar(100) DEFAULT NULL,
  `tc_test_content` varchar(100) DEFAULT NULL,
  `tc_test_step` varchar(1000) DEFAULT NULL,
  `tc_intend_output` varchar(200) DEFAULT NULL,
  `tc_type` int(11) DEFAULT NULL,
  `tc_flag` int(11) DEFAULT NULL,
  `tc_remark` varchar(1000) DEFAULT NULL,
  `tc_create_user` int(11) DEFAULT NULL,
  `tc_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`tc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=895 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case`
--

LOCK TABLES `test_case` WRITE;
/*!40000 ALTER TABLE `test_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_correct_record`
--

DROP TABLE IF EXISTS `test_correct_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_correct_record` (
  `tcr_id` int(11) NOT NULL AUTO_INCREMENT,
  `tcr_test_case` int(11) NOT NULL,
  `tcr_test_version` int(11) DEFAULT NULL,
  `tcr_oper_user` int(11) DEFAULT NULL,
  `tcr_oper_time` datetime DEFAULT NULL,
  `tcr_case_status` int(11) DEFAULT NULL,
  `tcr_remark` varchar(100) DEFAULT NULL,
  `tcr_test_result` int(11) DEFAULT NULL,
  PRIMARY KEY (`tcr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_correct_record`
--

LOCK TABLES `test_correct_record` WRITE;
/*!40000 ALTER TABLE `test_correct_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_correct_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_result`
--

DROP TABLE IF EXISTS `test_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_result` (
  `tr_id` int(11) NOT NULL AUTO_INCREMENT,
  `tr_name` varchar(45) DEFAULT NULL,
  `tr_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`tr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_result`
--

LOCK TABLES `test_result` WRITE;
/*!40000 ALTER TABLE `test_result` DISABLE KEYS */;
INSERT INTO `test_result` VALUES (1,'通过',0),(2,'未通过',0),(3,'NA',0);
/*!40000 ALTER TABLE `test_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr_account`
--

DROP TABLE IF EXISTS `usr_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usr_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(64) NOT NULL DEFAULT '',
  `password` varchar(64) DEFAULT NULL,
  `enabled` varchar(1) DEFAULT NULL,
  `PERSON_CODE` varchar(32) NOT NULL DEFAULT '',
  `PERSON_NAME` varchar(32) NOT NULL DEFAULT '',
  `SEX` varchar(1) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `DEPT` varchar(45) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `ENTRY_DATE` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `account_name_UNIQUE` (`account_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr_account`
--

LOCK TABLES `usr_account` WRITE;
/*!40000 ALTER TABLE `usr_account` DISABLE KEYS */;
INSERT INTO `usr_account` VALUES (1,'admin','21232F297A57A5A743894A0E4A801FC3','1','1','管理员','1','','','2',NULL,'2013-01-29');
/*!40000 ALTER TABLE `usr_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-27  9:13:48
