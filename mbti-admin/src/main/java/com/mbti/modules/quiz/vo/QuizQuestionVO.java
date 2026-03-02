package com.mbti.modules.quiz.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目VO（含选项）
 * @author: system
 */
@Data
public class QuizQuestionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer questionNo;
    private String title;
    private String description;
    private String questionType;
    private String required;
    private Integer score;
    
    /**
     * 选项列表
     */
    private List<QuizOptionVO> options;
}
