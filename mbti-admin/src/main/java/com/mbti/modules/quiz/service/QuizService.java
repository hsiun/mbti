package com.mbti.modules.quiz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mbti.modules.quiz.entity.QuizTestEntity;
import com.mbti.modules.quiz.vo.QuizSubmitDTO;
import com.mbti.modules.quiz.vo.QuizTestVO;
import com.mbti.modules.quiz.vo.QuizResultVO;

import java.util.List;

/**
 * 通用测试服务
 * @author: system
 */
public interface QuizService extends IService<QuizTestEntity> {

    /**
     * 获取测试列表
     */
    List<QuizTestEntity> listTests();

    /**
     * 获取测试详情（含题目和选项）
     */
    QuizTestVO getTestDetail(Long testId);

    /**
     * 提交答案并计算结果
     */
    QuizResultVO submitAnswer(QuizSubmitDTO submitDTO);

    /**
     * 获取用户历史记录
     */
    List<QuizResultVO> getHistory(Long userId, Long testId);
}
