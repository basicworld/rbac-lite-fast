-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: rbac-lite
-- ------------------------------------------------------
-- Server version	5.7.25

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
-- Table structure for table `sys_configuration`
--

DROP TABLE IF EXISTS `sys_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_configuration` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `config_name` varchar(255) NOT NULL COMMENT '配置项名称',
  `config_key` varchar(255) NOT NULL COMMENT '配置项标识符',
  `config_value` varchar(2000) DEFAULT NULL COMMENT '配置项键值',
  `config_value_type` varchar(255) NOT NULL COMMENT '配置项值的类型 String Integer Byte',
  `system_built` tinyint(3) unsigned NOT NULL COMMENT '是否系统内置 0-no 1-yes 内置的只能修改不能删除',
  `form_type` varchar(50) NOT NULL COMMENT '表单类型 string-文本输入框 password-密码输入框 radio-单选 checkbox-多选 单选和多选的选项来自optional_values',
  `optional_values` varchar(2000) DEFAULT NULL COMMENT '可选项，格式configvalue1:note1,configvalue2:note2',
  `visible` tinyint(3) unsigned NOT NULL COMMENT '是否可见 0-no 1-yes',
  `sort` int(10) unsigned zerofill NOT NULL COMMENT '排序',
  `default_value` varchar(2000) DEFAULT NULL COMMENT '默认值',
  `note` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key_UNIQUE` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_configuration`
--

LOCK TABLES `sys_configuration` WRITE;
/*!40000 ALTER TABLE `sys_configuration` DISABLE KEYS */;
INSERT INTO `sys_configuration` VALUES (1,'用户密码过期时间（天）','my.user.password.expire_time','600','integer',1,'string',NULL,1,0000000001,'30','整数数字，用户超过这个时间未修改密码的，将通过站内信发出提醒。','admin','2021-01-01 12:30:00','admin','2021-06-01 10:31:39'),(2,'邮件发送功能','my.system.mail.open','1','byte',1,'radio','1:启用,0:不启用',1,0000000008,NULL,'选择启用以开通邮件发送功能，启用前请确认邮箱参数配置正确。','admin','2021-01-01 12:30:00','admin','2021-05-10 14:56:38');
/*!40000 ALTER TABLE `sys_configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `parent_id` bigint(20) unsigned NOT NULL COMMENT '父菜单主键',
  `menu_name` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单路径',
  `component` varchar(255) NOT NULL DEFAULT '' COMMENT '组件标识',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_frame` tinyint(3) unsigned NOT NULL COMMENT '是否为外链: 0-no 1-yes',
  `visible` tinyint(3) unsigned NOT NULL COMMENT '是否可见: 0-no 1-yes',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态',
  `perms` varchar(255) NOT NULL DEFAULT '' COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '' COMMENT '图标标识',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(3) unsigned NOT NULL COMMENT '是否删除:0-no 1-yes',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='menu';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,0,'系统管理','/system','layout',0,0,1,0,'system','system',NULL,NULL,NULL,NULL,0,NULL),(2,1,'用户管理','user','system/user/index',0,0,1,0,'system:user',NULL,NULL,NULL,NULL,NULL,0,NULL),(3,1,'角色管理','role','system/role/index',0,0,1,0,'system:role',NULL,NULL,NULL,NULL,NULL,0,NULL),(4,1,'参数配置','config','system/config/index',0,0,1,0,'system:config','',NULL,NULL,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_message`
--

DROP TABLE IF EXISTS `sys_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `sender` varchar(255) NOT NULL DEFAULT '' COMMENT '发送人',
  `sender_id` bigint(20) unsigned DEFAULT NULL COMMENT '发送人主键',
  `receiver` varchar(255) NOT NULL DEFAULT '' COMMENT '收信人',
  `receiver_id` bigint(20) unsigned NOT NULL COMMENT '收信人主键',
  `title` varchar(1000) NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '内容',
  `visible` tinyint(3) unsigned NOT NULL COMMENT '是否可见 0-no 1-yes',
  `has_read` tinyint(3) unsigned NOT NULL COMMENT '是否已读 0-no 1-yes',
  `deleted` tinyint(3) unsigned NOT NULL COMMENT '是否已删除 0-no 1-yes',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_has_read_create_time` (`has_read`,`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_message`
--

LOCK TABLES `sys_message` WRITE;
/*!40000 ALTER TABLE `sys_message` DISABLE KEYS */;
INSERT INTO `sys_message` VALUES (1,'admin',NULL,'admin',1,'演示通知标题','演示通知内容。',1,1,0,NULL,'2021-01-01 11:00:00','超级管理员','2021-05-07 13:57:20');
/*!40000 ALTER TABLE `sys_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_message_model`
--

DROP TABLE IF EXISTS `sys_message_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message_model` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `model_key` varchar(255) NOT NULL COMMENT '模版唯一标识',
  `title_model` varchar(255) NOT NULL COMMENT '标题模版',
  `title_model_desc` varchar(1000) NOT NULL DEFAULT '' COMMENT '标题模版描述',
  `content_model` varchar(1000) NOT NULL COMMENT '内容模版',
  `content_model_desc` varchar(1000) NOT NULL DEFAULT '' COMMENT '内容模版描述',
  `send_system_message` tinyint(3) unsigned NOT NULL COMMENT '是否发送站内信 0-no 1-yes',
  `send_sms` tinyint(3) unsigned NOT NULL COMMENT '是否发送短信 0-no 1-yes',
  `send_email` tinyint(3) unsigned NOT NULL COMMENT '是否发送邮件 0-no 1-yes',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_model_key` (`model_key`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='消息模版表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_message_model`
--

LOCK TABLES `sys_message_model` WRITE;
/*!40000 ALTER TABLE `sys_message_model` DISABLE KEYS */;
INSERT INTO `sys_message_model` VALUES (1,'system:admin:changePassword','修改密码通知','','管理员 {0} 为你重置了密码。','{0}-管理员名称',1,0,0,NULL,'admin','2021-01-01 12:30:00',NULL,NULL),(2,'system:admin:disableUser','账号停用通知','','管理员 {0} 将你的账号设置为停用。','{0}-管理员名称',1,0,0,NULL,NULL,'2021-01-01 12:30:00',NULL,NULL),(3,'system:admin:enableUser','账号启用通知','','管理员 {0} 将你的账号设置为启用。','{0}-管理员名称',1,0,0,NULL,'admin','2021-01-01 12:30:00',NULL,NULL),(4,'system:admin:changeUserPermission','权限变更通知','','管理员 {0} 为你调整了账号权限。','{0}-管理员名称',1,0,0,NULL,'admin','2021-01-01 12:30:00',NULL,NULL),(5,'system:personal:passwordExpireWarning','密码过期预警','','你已经超过 {0} 天未修改密码，为了账号安全请及时修改密码。','{0}-密码过期天数',1,0,0,NULL,'admin','2021-01-01 12:30:00',NULL,NULL),(6,'system:personal:updateMyInfo','个人信息修改结果','','用户信息修改成功，当前昵称：{0}，当前邮箱：{1}，当前手机号：{2}。','{0}-昵称 {1}-邮箱 {2}-手机号',1,0,0,NULL,'admin','2021-01-01 12:30:00',NULL,NULL),(7,'system:personal:updatePassword','密码修改结果','','密码已修改成功。','',1,0,0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_message_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_name` varchar(100) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL DEFAULT '' COMMENT '角色唯一标识',
  `status` tinyint(3) unsigned NOT NULL COMMENT '角色状态',
  `sort` int(10) unsigned DEFAULT '0' COMMENT '排序',
  `data_scope` char(1) DEFAULT NULL COMMENT '角色数据权限标识',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(3) unsigned NOT NULL COMMENT '是否删除: 0-no 1-yes',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='role table of system';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',0,0,NULL,NULL,'2021-03-27 22:54:00',NULL,'2021-03-29 16:15:21',0,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色主键',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_sys_role_menu_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='role menu connection';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '唯一用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `status` tinyint(3) unsigned NOT NULL COMMENT '用户状态:0-normal 1-stop',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `pwd_update_time` datetime DEFAULT NULL COMMENT '最后密码更新时间',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(3) unsigned NOT NULL COMMENT '删除标记:0-no 1-yes',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_user_name` (`user_name`),
  KEY `idx_user_name_password` (`user_name`,`password`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='system user';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$LVOG5QyG.CfrJODnLXxaRu9DY20AEYbWO5f3HqU/N57QqYQj53jcS',0,'超级管理员',NULL,NULL,'2021-06-01 10:39:53',NULL,NULL,'2021-06-01 10:39:35',NULL,'2021-06-01 10:39:53',0,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_sys_user_role_id` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='user role connection';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1,1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-25 23:49:01
