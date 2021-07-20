/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : blogt

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 18/03/2021 17:28:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `is_show` tinyint(1) NULL DEFAULT 1 COMMENT '1显示 2不显示',
  `can_comment` tinyint(1) NULL DEFAULT 1 COMMENT '1可以评论 2不可以评论',
  `like_sum` bigint(15) NULL DEFAULT 0 COMMENT '点赞数',
  `read_sum` bigint(15) NULL DEFAULT 0 COMMENT '阅读数',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 3, '1231', 1, 1, 14, 14, '2021-03-05 17:16:18', '2021-03-09 17:42:15');
INSERT INTO `article` VALUES (2, 3, '1211', 1, 1, 111, 111, '2021-03-05 17:16:18', '2021-03-08 14:38:44');

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(11) NULL DEFAULT 0 COMMENT '评论父节点 如果为0则是评论文章',
  `article_id` bigint(11) NOT NULL COMMENT '文章ID',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_comment
-- ----------------------------
INSERT INTO `article_comment` VALUES (1, 0, 1, 3, 'test', '2021-03-12 06:53:09', '2021-03-12 06:53:09');
INSERT INTO `article_comment` VALUES (2, 1, 1, 3, 'test', '2021-03-12 06:53:23', '2021-03-12 06:53:23');
INSERT INTO `article_comment` VALUES (3, 1, 1, 3, 'test123', '2021-03-12 07:21:28', '2021-03-12 07:21:28');
INSERT INTO `article_comment` VALUES (4, 1, 1, 3, 'test123', '2021-03-12 07:21:40', '2021-03-12 07:21:40');

-- ----------------------------
-- Table structure for article_context
-- ----------------------------
DROP TABLE IF EXISTS `article_context`;
CREATE TABLE `article_context`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `context` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `article_id` bigint(11) NULL DEFAULT NULL COMMENT '对应文章ID',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_context
-- ----------------------------

-- ----------------------------
-- Table structure for article_like
-- ----------------------------
DROP TABLE IF EXISTS `article_like`;
CREATE TABLE `article_like`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NULL DEFAULT NULL,
  `article_id` bigint(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article`(`article_id`) USING BTREE,
  INDEX `user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_like
-- ----------------------------
INSERT INTO `article_like` VALUES (1, 2, 1, '2021-03-08 14:38:59', '2021-03-09 15:23:55');
INSERT INTO `article_like` VALUES (2, 1, 1, '2021-03-08 14:38:59', '2021-03-08 16:22:29');
INSERT INTO `article_like` VALUES (9, 3, 1, '2021-03-09 07:24:53', '2021-03-09 07:24:53');

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `article_id` bigint(11) NULL DEFAULT NULL COMMENT '文章ID',
  `tag_id` bigint(11) NULL DEFAULT NULL COMMENT '标签ID',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tag`(`tag_id`) USING BTREE,
  INDEX `article`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签表和文章表多对多中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES (1, 1, 1, '2021-03-05 17:16:10', '2021-03-05 17:16:11');
INSERT INTO `article_tag` VALUES (2, 2, 1, '2021-03-05 17:16:33', '2021-03-05 17:16:18');
INSERT INTO `article_tag` VALUES (3, 1, 3, '2021-03-05 17:17:27', '2021-03-05 17:17:30');

-- ----------------------------
-- Table structure for article_visitor
-- ----------------------------
DROP TABLE IF EXISTS `article_visitor`;
CREATE TABLE `article_visitor`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NULL DEFAULT 0 COMMENT '观看的用户，如果是游客，则记录为0',
  `article_id` bigint(11) NULL DEFAULT NULL COMMENT '观看的文章',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '观看的IP地址',
  `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '浏览文章记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_visitor
-- ----------------------------
INSERT INTO `article_visitor` VALUES (1, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:31:04');
INSERT INTO `article_visitor` VALUES (2, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:32:42');
INSERT INTO `article_visitor` VALUES (3, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:33:48');
INSERT INTO `article_visitor` VALUES (4, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:33:49');
INSERT INTO `article_visitor` VALUES (5, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:41:43');
INSERT INTO `article_visitor` VALUES (6, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:41:59');
INSERT INTO `article_visitor` VALUES (7, 0, 1, '192.168.5.214', '1231fsdf', '2021-03-09 09:42:16');

-- ----------------------------
-- Table structure for family
-- ----------------------------
DROP TABLE IF EXISTS `family`;
CREATE TABLE `family`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家族名',
  `Introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '家庭' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of family
-- ----------------------------

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_name` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径/名字',
  `sha512` varchar(550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (10, 'static\\upload\\image\\QQ截图20210224174038_4ieCr1614160525328.png', 'ea216cc1d8f35d334289d73d044fa1f038786b6c68b3a69027af4a134f0c002f76fc5214779c535e2185e0a74f7b983fa707ec66dcf510b9f9e5e8452e3cc006', '2021-02-24 09:55:28');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'nametag', '2021-03-05 17:16:18', '2021-03-05 17:16:18');
INSERT INTO `tag` VALUES (2, 'tagname 2', '2021-03-05 17:16:18', '2021-03-05 17:16:18');
INSERT INTO `tag` VALUES (3, 'test333', '2021-03-05 17:16:18', '2021-03-06 15:26:43');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名 暂定唯一',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码盐',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `family_id` bigint(11) NULL DEFAULT 0 COMMENT '家庭ID',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `family`(`family_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123', '123', '579d9ec9d0c3d687aaa91289ac2854e4', '123', '123', 0, '2021-02-24 16:30:36', '2021-02-24 17:07:43');
INSERT INTO `user` VALUES (3, 'aaa', '111', 'a7fed51926893734a56bcaa7af704fc9', 'gLKJ7', '1324567910', 0, '2021-02-25 08:51:30', '2021-02-25 08:51:30');

-- ----------------------------
-- Table structure for user_photo
-- ----------------------------
DROP TABLE IF EXISTS `user_photo`;
CREATE TABLE `user_photo`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NULL DEFAULT NULL,
  `image_id` bigint(11) NULL DEFAULT NULL COMMENT '照片id',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '1公开  2家庭成员  3自己',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户图片库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_photo
-- ----------------------------
INSERT INTO `user_photo` VALUES (2, 1, 1, 1, '2021-03-10 08:12:24', '2021-03-10 08:12:24');
INSERT INTO `user_photo` VALUES (3, 1, 1, 1, '2021-03-10 08:12:24', '2021-03-10 08:12:24');

SET FOREIGN_KEY_CHECKS = 1;
