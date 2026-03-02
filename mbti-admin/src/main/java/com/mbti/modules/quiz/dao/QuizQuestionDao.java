package com.mbti.modules.quiz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbti.modules.quiz.entity.QuizQuestionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目DAO
 * @author: system
 */
@Mapper
public interface QuizQuestionDao extends BaseMapper<QuizQuestionEntity> {
}
