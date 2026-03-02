package com.mbti.modules.quiz.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 测试详情VO（含题目和选项）
 * @author: system
 */
@Data
public class QuizTestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String description;
    private String coverImage;
    private String resultType;
    
    /**
     * 题目列表
     */
    private List<QuizQuestionVO> questions;
    
    /**
     * 维度列表（类型型测试）
     */
    private List<QuizDimensionVO> dimensions;
}
