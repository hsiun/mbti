package com.mbti.modules.quiz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbti.modules.quiz.entity.QuizDimensionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 维度DAO
 * @author: system
 */
@Mapper
public interface QuizDimensionDao extends BaseMapper<QuizDimensionEntity> {
}
