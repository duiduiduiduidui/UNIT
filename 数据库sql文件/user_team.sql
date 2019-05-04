/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-28 14:28:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user_team`
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team` (
  `user` varchar(255) NOT NULL,
  `team` int(20) NOT NULL,
  `identity` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`user`,`team`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_team
-- ----------------------------
INSERT INTO `user_team` VALUES ('1', '1', '1');
INSERT INTO `user_team` VALUES ('1', '2', '1');
INSERT INTO `user_team` VALUES ('2', '1', '2');
INSERT INTO `user_team` VALUES ('2', '2', '2');
INSERT INTO `user_team` VALUES ('3', '1', '2');
INSERT INTO `user_team` VALUES ('3', '2', '2');
