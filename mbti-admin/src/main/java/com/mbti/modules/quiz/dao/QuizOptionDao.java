package com.mbti.modules.quiz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbti.modules.quiz.entity.QuizOptionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 选项DAO
 * @author: system
 */
@Mapper
public interface QuizOptionDao extends BaseMapper<QuizOptionEntity> {
}
