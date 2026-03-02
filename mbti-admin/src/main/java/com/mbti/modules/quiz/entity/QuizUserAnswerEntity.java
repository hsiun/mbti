package com.mbti.modules.quiz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户测试记录实体
 * @author: system
 * @date: 2026-02-28
 */
@Data
@TableName("quiz_user_answer")
public class QuizUserAnswerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 测试ID
     */
    private Long testId;
    
    /**
     * 用户ID（登录用户）
     */
    private Long userId;
    
    /**
     * 会话ID（匿名用户）
     */
    private String sessionId;
    
    /**
     * 答题数据JSON
     */
    private String answerData;
    
    /**
     * 总得分
     */
    private Integer totalScore;
    
    /**
     * 维度得分JSON
     */
    private String dimensionScores;
    
    /**
     * 匹配的结果编码
     */
    private String resultCode;
    
    /**
     * 结果标题
     */
    private String resultTitle;
    
    /**
     * 完整结果数据JSON
     */
    private String resultData;
    
    /**
     * 耗时（秒）
     */
    private Integer timeSpent;
    
    /**
     * IP地址
     */
    private String ipAddress;
    
    /**
     * 删除标记
     */
    private String delFlag;
    
    /**
     * 创建时间
     */
    private Date createTime;
}
