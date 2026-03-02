package com.mbti.modules.quiz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试类型实体
 * @author: system
 * @date: 2026-02-28
 */
@Data
@TableName("quiz_test")
public class QuizTestEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 测试编码（唯一标识）
     */
    private String code;
    
    /**
     * 测试名称
     */
    private String name;
    
    /**
     * 测试描述
     */
    private String description;
    
    /**
     * 封面图片
     */
    private String coverImage;
    
    /**
     * 结果类型：SCORE-分数型, TYPE-类型型, LEVEL-等级型
     */
    private String resultType;
    
    /**
     * 计分规则JSON（可配置）
     */
    private String scoringRule;
    
    /**
     * 状态：Y-启用, N-禁用
     */
    private String status;
    
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
