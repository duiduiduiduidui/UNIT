/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:27:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `team` int(20) DEFAULT NULL,
  `member` varchar(255) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `month` int(2) DEFAULT NULL,
  `day` int(2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `is_overdue` tinyint(4) DEFAULT NULL,
  `is_proposed` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
