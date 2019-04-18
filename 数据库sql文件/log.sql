/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:27:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `mission` int(30) DEFAULT NULL,
  `describe` int(4) DEFAULT NULL,
  `member` varchar(255) DEFAULT NULL,
  `is_viewed` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
