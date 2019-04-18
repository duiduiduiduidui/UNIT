/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:27:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for board
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
  `id` int(20) NOT NULL,
  `team` int(20) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `member` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
