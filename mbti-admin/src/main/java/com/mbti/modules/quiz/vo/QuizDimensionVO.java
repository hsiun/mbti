package com.mbti.modules.quiz.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 维度VO
 * @author: system
 */
@Data
public class QuizDimensionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String dimensionCode;
    private String dimensionName;
    private String description;
}
