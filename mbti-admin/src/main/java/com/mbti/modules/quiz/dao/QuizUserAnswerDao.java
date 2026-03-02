package com.mbti.modules.quiz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbti.modules.quiz.entity.QuizUserAnswerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户答题记录DAO
 * @author: system
 */
@Mapper
public interface QuizUserAnswerDao extends BaseMapper<QuizUserAnswerEntity> {
}
