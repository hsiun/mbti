package com.mbti.modules.quiz.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 提交答案DTO
 * @author: system
 */
@Data
public class QuizSubmitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 测试ID
     */
    private Long testId;
    
    /**
     * 用户ID（可选）
     */
    private Long userId;
    
    /**
     * 会话ID（可选）
     */
    private String sessionId;
    
    /**
     * 答题列表
     */
    private List<AnswerItem> answers;
    
    /**
     * 耗时（秒）
     */
    private Integer timeSpent;
    
    @Data
    public static class AnswerItem implements Serializable {
        /**
         * 题目ID
         */
        private Long questionId;
        
        /**
         * 选中的选项编码列表
         */
        private List<String> optionCodes;
    }
}
