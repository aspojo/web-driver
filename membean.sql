/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : membean

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 11/06/2022 06:08:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rootcasts
-- ----------------------------
DROP TABLE IF EXISTS `rootcasts`;
CREATE TABLE `rootcasts` (
  `title` text NOT NULL,
  `subtitle` text,
  `description` text,
  `content` longtext,
  `href` text,
  `_key` varchar(100) NOT NULL,
  PRIMARY KEY (`_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for roots
-- ----------------------------
DROP TABLE IF EXISTS `roots`;
CREATE TABLE `roots` (
  `root` text NOT NULL,
  `has_audio` tinyint(1) DEFAULT NULL,
  `common` tinyint(1) DEFAULT NULL,
  `title` text,
  `description` text,
  `content` longtext,
  `_key` varchar(100) NOT NULL,
  `related` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
