# ************************************************************
# Sequel Ace SQL dump
# 版本号： 3028
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 5.7.29)
# 数据库: rbac-lite
# 生成时间: 2021-04-27 14:22:50 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='menu';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `path`, `component`, `sort`, `is_frame`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `note`)
VALUES
	(1,0,'系统管理','/system','layout',0,0,1,0,'system','system',NULL,NULL,NULL,NULL,0,NULL),
	(2,1,'用户管理','user','system/user/index',0,0,1,0,'system:user',NULL,NULL,NULL,NULL,NULL,0,NULL),
	(3,1,'角色管理','role','system/role/index',0,0,1,0,'system:role',NULL,NULL,NULL,NULL,NULL,0,NULL);

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='role table of system';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `role_name`, `role_key`, `status`, `sort`, `data_scope`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `note`)
VALUES
	(1,'超级管理员','admin',0,0,NULL,NULL,'2021-03-27 22:54:00',NULL,'2021-03-29 16:15:21',0,NULL);

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色主键',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_sys_role_menu_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='role menu connection';

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;

INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`)
VALUES
	(1,1,1),
	(2,1,2),
	(3,1,3);

/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='system user';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `user_name`, `password`, `status`, `nick_name`, `phone`, `email`, `pwd_update_time`, `dept_name`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `note`)
VALUES
	(1,'admin','$2a$10$ceAT6b6zXRlpjCx8WAfdteI/.krXzeh15E0kQJubQ7CwuJHsSkAke',0,'超级管理员','','','2021-03-29 17:05:02',NULL,NULL,'2021-03-27 18:34:41',NULL,'2021-03-29 17:05:02',0,NULL);

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_sys_user_role_id` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='user role connection';

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;

INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`)
VALUES
	(1,1,1);

/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
