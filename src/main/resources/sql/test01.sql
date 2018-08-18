/*
Navicat MySQL Data Transfer

Source Server         : 172.17.17.89
Source Server Version : 50720
Source Host           : 172.17.17.89:3306
Source Database       : test01

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-08-18 15:52:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `url` varchar(45) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `image_url` varchar(45) DEFAULT NULL,
  `menu_name` varchar(45) DEFAULT NULL,
  `menu_order` varchar(45) DEFAULT NULL,
  `next_url` varchar(45) DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='权限资源表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'ROLE_HOME', '根菜单目录', '/menu', null, null, null, '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('2', 'ROLE_ADMIN', 'admin登录', '/admin', null, null, null, '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('3', 'ROLE_USER_PWD', '密码设置', '/user/pwd', null, null, '密码设置', '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('10', 'ROLE_OWNER', '用户管理', '/user', null, 'images/u32.png', '用户管理', '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('11', 'ROLE_OWNER_MANAGE', '用户管理', '/user/view', '10', null, '用户管理', '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('12', 'ROLE_BUILDING', '角色管理', '/role', null, 'images/u12.png', '角色管理', '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('13', 'ROLE_BUILDING_MANAGE', '角色管理', '/role/view', '12', null, '角色管理', '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('14', 'ROLE_RECORD', '资源管理', '/permission', null, 'images/u44.png', '资源管理', '0', null, '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_permission` VALUES ('15', 'ROLE_RECORD_MANAGE', '资源管理', '/permission/view', '14', null, '资源管理', '0', null, '1', '2018-03-28 18:28:07', null);

-- ----------------------------
-- Table structure for sys_permission_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_role`;
CREATE TABLE `sys_permission_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_fk_idx` (`permission_id`),
  KEY `role_fk_idx` (`role_id`),
  CONSTRAINT `permission_fk` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of sys_permission_role
-- ----------------------------
INSERT INTO `sys_permission_role` VALUES ('1', '1', '10', '1', '2018-08-15 15:50:34', null);
INSERT INTO `sys_permission_role` VALUES ('2', '1', '11', '1', '2018-08-15 15:53:25', null);
INSERT INTO `sys_permission_role` VALUES ('3', '1', '12', '1', '2018-08-15 15:53:37', null);
INSERT INTO `sys_permission_role` VALUES ('4', '1', '13', '1', '2018-08-15 15:53:43', null);
INSERT INTO `sys_permission_role` VALUES ('5', '1', '14', '1', '2018-08-15 15:53:49', null);
INSERT INTO `sys_permission_role` VALUES ('6', '1', '15', '1', '2018-08-15 15:53:55', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ROLE_ADMIN', '1', '2018-03-28 18:28:07', null, '管理员');
INSERT INTO `sys_role` VALUES ('2', 'ROLE_SALER', '1', '2018-08-15 17:01:45', null, '销售员');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_user_id` bigint(20) DEFAULT NULL,
  `sys_role_id` bigint(20) DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_pk_idx` (`sys_user_id`),
  KEY `role_pk_idx` (`sys_role_id`),
  CONSTRAINT `role_pk` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_pk` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '5', '1', '1', '2018-08-15 15:50:09', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) DEFAULT NULL COMMENT '学校/小区id',
  `user_name` varchar(45) DEFAULT NULL,
  `full_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_school_fk_idx` (`school_id`),
  CONSTRAINT `user_school_fk` FOREIGN KEY (`school_id`) REFERENCES `tb_school` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('5', '1', 'admin', 'admin', 'db40fad6a0f4d8d5cb145bf90625b0bf', '18506107646', '123123@qq.com', '1', '2018-03-28 18:28:07', null);
INSERT INTO `sys_user` VALUES ('21', null, 'accper', '陶冶', null, '13297828765', '1805023826@qq.com', '1', '2018-08-15 17:39:50', null);
INSERT INTO `sys_user` VALUES ('23', null, 'demo1', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:08:55', null);
INSERT INTO `sys_user` VALUES ('25', null, 'demo2', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:08', null);
INSERT INTO `sys_user` VALUES ('27', null, 'demo3', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:09', null);
INSERT INTO `sys_user` VALUES ('29', null, 'demo4', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:10', null);
INSERT INTO `sys_user` VALUES ('31', null, 'demo5', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:11', null);
INSERT INTO `sys_user` VALUES ('33', null, 'demo6', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:11', null);
INSERT INTO `sys_user` VALUES ('35', null, 'demo7', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:12', null);
INSERT INTO `sys_user` VALUES ('37', null, 'demo8', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:13', null);
INSERT INTO `sys_user` VALUES ('39', null, 'demo9', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:14', null);
INSERT INTO `sys_user` VALUES ('41', null, 'demo10', '演示', null, '13297828765', '1805023826@qq.com', '1', '2018-08-16 09:09:15', null);
INSERT INTO `sys_user` VALUES ('43', null, 'demo11', '演示', null, '13297828765', '1805023826@qq.com', '0', '2018-08-16 09:09:17', null);
