/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:27:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for team
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
  `year` int(4) DEFAULT NULL,
  `month` int(2) DEFAULT NULL,
  `day` int(2) DEFAULT NULL,
  `is_full` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
