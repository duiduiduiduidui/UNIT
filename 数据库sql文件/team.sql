/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-28 14:28:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `team`
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `teamtype` varchar(255) DEFAULT NULL,
  `teamtype_small` varchar(255) DEFAULT NULL,
  `captain` varchar(255) DEFAULT NULL,
  `number_max` int(10) DEFAULT NULL,
  `number_now` int(10) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(2) DEFAULT NULL,
  `day` varchar(2) DEFAULT NULL,
  `is_full` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('1', '对对对', '长期组队', '计算机大赛', '1', '5', '3', '1', '加油', '2019', '04', '19', '0');
INSERT INTO `team` VALUES ('2', 'iTrip', '长期组队', '计算机大赛', '1', '5', '3', '1', '喝酒', '2019', '04', '23', '0');
