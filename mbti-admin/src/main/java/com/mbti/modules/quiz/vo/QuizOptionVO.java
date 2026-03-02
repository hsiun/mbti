package com.mbti.modules.quiz.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 选项VO
 * @author: system
 */
@Data
public class QuizOptionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String optionCode;
    private String content;
    private Integer score;
    private String dimension;
    private Integer dimensionScore;
}
