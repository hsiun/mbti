package com.mbti.modules.quiz.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 测试结果VO
 * @author: system
 */
@Data
public class QuizResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long recordId;
    
    /**
     * 测试ID
     */
    private Long testId;
    
    /**
     * 总得分
     */
    private Integer totalScore;
    
    /**
     * 各维度得分
     */
    private Map<String, Integer> dimensionScores;
    
    /**
     * 结果编码
     */
    private String resultCode;
    
    /**
     * 结果标题
     */
    private String resultTitle;
    
    /**
     * 结果描述
     */
    private String description;
    
    /**
     * 建议
     */
    private String suggestion;
    
    /**
     * 结果图片
     */
    private String imageUrl;
}
