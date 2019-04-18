/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:27:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_team
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team` (
  `user` varchar(255) NOT NULL,
  `team` int(20) NOT NULL,
  `identity` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`user`,`team`)
)
