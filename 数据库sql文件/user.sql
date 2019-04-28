/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-28 14:28:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `openid` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `credit` int(50) DEFAULT NULL,
  `tech` double(50,0) DEFAULT NULL,
  `team_sprit` double(50,0) DEFAULT NULL,
  `time_keeping` double(50,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '刘小逗', 'liufanmett@163.com', '15921606915', '60', '60', '60', '60');
INSERT INTO `user` VALUES ('2', '崔明豪', '两罚皆阿斯兰的空间', 'sdfsjdl', '60', '6', '60', '60');
INSERT INTO `user` VALUES ('3', '游家振', 'daf', 'dsaf', '60', '60', '60', '60');
