package com.mbti.modules.quiz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目实体
 * @author: system
 * @date: 2026-02-28
 */
@Data
@TableName("quiz_question")
public class QuizQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 测试ID
     */
    private Long testId;
    
    /**
     * 题号
     */
    private Integer questionNo;
    
    /**
     * 题目内容
     */
    private String title;
    
    /**
     * 题目说明/图片
     */
    private String description;
    
    /**
     * 题目类型：SINGLE-单选, MULTIPLE-多选, JUDGE-判断
     */
    private String questionType;
    
    /**
     * 是否必答
     */
    private String required;
    
    /**
     * 题目分值（用于分数型测试）
     */
    private Integer score;
    
    /**
     * 题目解析
     */
    private String analysis;
    
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
    
    /**
     * 修改人
     */
    private String updateUser;
    
    /**
     * 修改时间
     */
    private Date updateTime;
}
