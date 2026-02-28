package com.mbti.modules.quiz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbti.modules.quiz.entity.QuizTestEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试类型DAO
 * @author: system
 */
@Mapper
public interface QuizTestDao extends BaseMapper<QuizTestEntity> {
}
