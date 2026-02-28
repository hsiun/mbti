-- =====================================================
-- 通用测试系统数据库表设计
-- 支持多种测试类型：MBTI、心理测试、知识问答等
-- =====================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- 1. 测试类型表
-- =====================================================
DROP TABLE IF EXISTS `quiz_test`;
CREATE TABLE `quiz_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '测试编码（唯一标识）',
  `name` varchar(255) NOT NULL COMMENT '测试名称',
  `description` text COMMENT '测试描述',
  `cover_image` varchar(500) DEFAULT NULL COMMENT '封面图片',
  `result_type` varchar(20) NOT NULL DEFAULT 'SCORE' COMMENT '结果类型：SCORE-分数型, TYPE-类型型, LEVEL-等级型',
  `scoring_rule` text COMMENT '计分规则JSON（可配置）',
  `status` char(1) DEFAULT 'Y' COMMENT '状态：Y-启用, N-禁用',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试类型表';

-- =====================================================
-- 2. 维度定义表（用于MBTI等类型测试）
-- =====================================================
DROP TABLE IF EXISTS `quiz_dimension`;
CREATE TABLE `quiz_dimension` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `test_id` bigint(20) NOT NULL COMMENT '测试ID',
  `dimension_code` varchar(50) NOT NULL COMMENT '维度编码',
  `dimension_name` varchar(100) NOT NULL COMMENT '维度名称',
  `description` text COMMENT '维度描述',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_test_id` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维度定义表';

-- =====================================================
-- 3. 题目表
-- =====================================================
DROP TABLE IF EXISTS `quiz_question`;
CREATE TABLE `quiz_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `test_id` bigint(20) NOT NULL COMMENT '测试ID',
  `question_no` int(11) NOT NULL COMMENT '题号',
  `title` text NOT NULL COMMENT '题目内容',
  `description` text COMMENT '题目说明/图片',
  `question_type` varchar(20) NOT NULL DEFAULT 'SINGLE' COMMENT '题目类型：SINGLE-单选, MULTIPLE-多选, JUDGE-判断',
  `required` char(1) DEFAULT 'Y' COMMENT '是否必答',
  `score` int(11) DEFAULT 1 COMMENT '题目分值（用于分数型测试）',
  `analysis` text COMMENT '题目解析',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_test_id` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';

-- =====================================================
-- 4. 选项表
-- =====================================================
DROP TABLE IF EXISTS `quiz_option`;
CREATE TABLE `quiz_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `question_id` bigint(20) NOT NULL COMMENT '题目ID',
  `option_code` varchar(10) NOT NULL COMMENT '选项编码（A/B/C/D或1/2/3）',
  `content` text NOT NULL COMMENT '选项内容',
  `is_correct` char(1) DEFAULT 'N' COMMENT '是否正确答案（判断题/知识测试用）',
  `score` int(11) DEFAULT 0 COMMENT '选项分值',
  `dimension` varchar(50) DEFAULT NULL COMMENT '维度标识（如MBTI的E/I/S/N等）',
  `dimension_score` int(11) DEFAULT 1 COMMENT '维度得分',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选项表';

-- =====================================================
-- 5. 测试结果规则表
-- =====================================================
DROP TABLE IF EXISTS `quiz_result_rule`;
CREATE TABLE `quiz_result_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `test_id` bigint(20) NOT NULL COMMENT '测试ID',
  `rule_name` varchar(100) NOT NULL COMMENT '规则名称',
  `result_code` varchar(50) NOT NULL COMMENT '结果编码',
  `result_title` varchar(255) DEFAULT NULL COMMENT '结果标题',
  `min_score` int(11) DEFAULT NULL COMMENT '最小分数（分数型）',
  `max_score` int(11) DEFAULT NULL COMMENT '最大分数（分数型）',
  `dimension_values` text COMMENT '维度值JSON（类型型，如{"E":8,"I":4}）',
  `priority` int(11) DEFAULT 0 COMMENT '优先级（多规则匹配时）',
  `description` text COMMENT '结果描述',
  `suggestion` text COMMENT '建议/指导',
  `image_url` varchar(500) DEFAULT NULL COMMENT '结果图片',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_test_id` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试结果规则表';

-- =====================================================
-- 6. 用户测试记录表
-- =====================================================
DROP TABLE IF EXISTS `quiz_user_answer`;
CREATE TABLE `quiz_user_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `test_id` bigint(20) NOT NULL COMMENT '测试ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID（登录用户）',
  `session_id` varchar(100) DEFAULT NULL COMMENT '会话ID（匿名用户）',
  `answer_data` text COMMENT '答题数据JSON',
  `total_score` int(11) DEFAULT NULL COMMENT '总得分',
  `dimension_scores` text COMMENT '维度得分JSON',
  `result_code` varchar(50) DEFAULT NULL COMMENT '匹配的结果编码',
  `result_title` varchar(255) DEFAULT NULL COMMENT '结果标题',
  `result_data` text COMMENT '完整结果数据JSON',
  `time_spent` int(11) DEFAULT NULL COMMENT '耗时（秒）',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_test_user` (`test_id`, `user_id`),
  KEY `idx_session_id` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户测试记录表';

-- =====================================================
-- 初始化数据：迁移MBTI测试
-- =====================================================

-- 插入MBTI测试
INSERT INTO `quiz_test` (`id`, `code`, `name`, `description`, `result_type`, `status`, `sort`, `del_flag`, `create_time`)
VALUES (1, 'MBTI', 'MBTI性格测试（93题）', 'MBTI职业性格测试是一种迫选型、自我报告式的性格评估测试，用以衡量和描述人们在获取信息、作出决策、对待生活等方面的心理活动规律和性格类型。', 'TYPE', 'Y', 1, 'N', NOW());

-- 插入MBTI维度
INSERT INTO `quiz_dimension` (`test_id`, `dimension_code`, `dimension_name`, `description`, `sort`) VALUES
(1, 'E', '外向', '从人际交往中获得能量', 1),
(1, 'I', '内向', '从独处中获得能量', 2),
(1, 'S', '感觉', '关注现实和细节', 3),
(1, 'N', '直觉', '关注可能性和大局', 4),
(1, 'T', '思考', '基于逻辑做决策', 5),
(1, 'F', '情感', '基于价值观做决策', 6),
(1, 'J', '判断', '喜欢有计划的生活', 7),
(1, 'P', '知觉', '喜欢灵活的生活', 8);

-- 插入心理测试
INSERT INTO `quiz_test` (`id`, `code`, `name`, `description`, `result_type`, `status`, `sort`, `del_flag`, `create_time`)
VALUES (2, 'PSYCHO_ANGER', '心理测试：看看什么会激怒你', '测试什么情况最容易让你生气', 'SCORE', 'Y', 2, 'N', NOW());

SET FOREIGN_KEY_CHECKS = 1;
