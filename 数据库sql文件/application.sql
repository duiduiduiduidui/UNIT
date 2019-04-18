/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:26:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `user` varchar(255) DEFAULT NULL,
  `team` int(20) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL
)