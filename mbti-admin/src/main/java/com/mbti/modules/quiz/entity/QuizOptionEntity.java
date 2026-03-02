package com.mbti.modules.quiz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 选项实体
 * @author: system
 * @date: 2026-02-28
 */
@Data
@TableName("quiz_option")
public class QuizOptionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 题目ID
     */
    private Long questionId;
    
    /**
     * 选项编码（A/B/C/D或1/2/3）
     */
    private String optionCode;
    
    /**
     * 选项内容
     */
    private String content;
    
    /**
     * 是否正确答案（判断题/知识测试用）
     */
    private String isCorrect;
    
    /**
     * 选项分值
     */
    private Integer score;
    
    /**
     * 维度标识（如MBTI的E/I/S/N等）
     */
    private String dimension;
    
    /**
     * 维度得分
     */
    private Integer dimensionScore;
    
    /**
     * 排序
     */
    private Integer sort;
    
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
