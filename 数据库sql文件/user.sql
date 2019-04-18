/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-18 15:27:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `openid` varchar(255) DEFAULT NULL,
  `neckname` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `credit` int(50) DEFAULT NULL,
  `tech` double(50,0) DEFAULT NULL,
  `team_sprit` double(50,0) DEFAULT NULL,
  `time_keeping` double(50,0) DEFAULT NULL
)
