package com.mbti.modules.quiz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试结果规则实体
 * @author: system
 * @date: 2026-02-28
 */
@Data
@TableName("quiz_result_rule")
public class QuizResultRuleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 测试ID
     */
    private Long testId;
    
    /**
     * 规则名称
     */
    private String ruleName;
    
    /**
     * 结果编码
     */
    private String resultCode;
    
    /**
     * 结果标题
     */
    private String resultTitle;
    
    /**
     * 最小分数（分数型）
     */
    private Integer minScore;
    
    /**
     * 最大分数（分数型）
     */
    private Integer maxScore;
    
    /**
     * 维度值JSON（类型型，如{"E":8,"I":4}）
     */
    private String dimensionValues;
    
    /**
     * 优先级（多规则匹配时）
     */
    private Integer priority;
    
    /**
     * 结果描述
     */
    private String description;
    
    /**
     * 建议/指导
     */
    private String suggestion;
    
    /**
     * 结果图片
     */
    private String imageUrl;
    
    /**
     * 删除标记
     */
    private String delFlag;
    
    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 创建时间
     */
    private Date createTime;
}
