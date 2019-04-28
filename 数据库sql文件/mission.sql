/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : unit

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-28 14:28:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mission`
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `team` int(20) DEFAULT NULL,
  `member` varchar(255) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(2) DEFAULT NULL,
  `day` varchar(2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `is_overdue` tinyint(4) DEFAULT NULL,
  `is_proposed` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mission
-- ----------------------------
INSERT INTO `mission` VALUES ('1', '1', '1', '2019', '04', '22', '加油', '0', '0', '0');
INSERT INTO `mission` VALUES ('2', '1', '1', '2019', '04', '25', '距离', '0', '0', '1');
INSERT INTO `mission` VALUES ('3', '1', '1', '2019', '04', '28', '喝酒', '0', '0', '0');
INSERT INTO `mission` VALUES ('4', '1', '1', '2019', '04', '27', '加油', '0', '0', '0');
INSERT INTO `mission` VALUES ('5', '1', '2', '2019', '04', '22', '加油', '0', '0', '0');
INSERT INTO `mission` VALUES ('6', '1', '2', '2019', '04', '25', '加油', '0', '0', '1');
INSERT INTO `mission` VALUES ('7', '1', '2', '2019', '04', '27', '喝酒', '0', '0', '1');
INSERT INTO `mission` VALUES ('8', '1', '2', '2019', '04', '28', '6', '0', '0', '0');
INSERT INTO `mission` VALUES ('9', '1', '3', '2019', '04', '22', '喝酒', '0', '0', '1');
INSERT INTO `mission` VALUES ('10', '1', '3', '2019', '04', '25', '2', '0', '0', '0');
INSERT INTO `mission` VALUES ('11', '2', '1', '2019', '04', '22', '加油', '0', '0', '0');
INSERT INTO `mission` VALUES ('12', '2', '1', '2019', '04', '24', '喝酒', '0', '0', '0');
INSERT INTO `mission` VALUES ('13', '2', '1', '2019', '04', '28', '总决赛', '0', '0', '0');
INSERT INTO `mission` VALUES ('14', '2', '2', '2019', '04', '05', '加油', '0', '0', '0');
INSERT INTO `mission` VALUES ('15', '2', '2', '2019', '05', '12', '干啥', '0', '0', '0');
INSERT INTO `mission` VALUES ('16', '2', '3', '2019', '04', '29', '加油类', '0', '0', '0');
INSERT INTO `mission` VALUES ('17', '2', '3', '2019', '04', '22', '三分', '0', '0', '0');
INSERT INTO `mission` VALUES ('18', '1', '1', '2019', '04', '26', '哈哈哈', null, null, null);
INSERT INTO `mission` VALUES ('19', '1', '1', '2019', '04', '25', '哈哈哈', null, null, null);
