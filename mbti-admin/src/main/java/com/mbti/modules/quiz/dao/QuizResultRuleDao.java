package com.mbti.modules.quiz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbti.modules.quiz.entity.QuizResultRuleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 结果规则DAO
 * @author: system
 */
@Mapper
public interface QuizResultRuleDao extends BaseMapper<QuizResultRuleEntity> {
}
