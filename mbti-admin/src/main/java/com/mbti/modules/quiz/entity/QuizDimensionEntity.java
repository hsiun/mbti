package com.mbti.modules.quiz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 维度定义实体
 * @author: system
 * @date: 2026-02-28
 */
@Data
@TableName("quiz_dimension")
public class QuizDimensionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 测试ID
     */
    private Long testId;
    
    /**
     * 维度编码
     */
    private String dimensionCode;
    
    /**
     * 维度名称
     */
    private String dimensionName;
    
    /**
     * 维度描述
     */
    private String description;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 删除标记
     */
    private String delFlag;
}
