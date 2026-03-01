package com.mbti.modules.quiz.controller;

import com.mbti.common.utils.RespResult;
import com.mbti.modules.quiz.entity.QuizTestEntity;
import com.mbti.modules.quiz.service.QuizService;
import com.mbti.modules.quiz.vo.QuizResultVO;
import com.mbti.modules.quiz.vo.QuizSubmitDTO;
import com.mbti.modules.quiz.vo.QuizTestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通用测试Controller
 * @author: system
 */
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    /**
     * 获取测试列表
     */
    @GetMapping("/list")
    public RespResult list() {
        List<QuizTestEntity> list = quizService.listTests();
        return new RespResult(RespResult.SUCCESS, list);
    }

    /**
     * 获取测试详情（含题目和选项）
     */
    @GetMapping("/detail/{testId}")
    public RespResult detail(@PathVariable("testId") Long testId) {
        QuizTestVO vo = quizService.getTestDetail(testId);
        return new RespResult(RespResult.SUCCESS, vo);
    }

    /**
     * 提交答案并获取结果
     */
    @PostMapping("/submit")
    public RespResult submit(@RequestBody QuizSubmitDTO submitDTO) {
        QuizResultVO result = quizService.submitAnswer(submitDTO);
        return new RespResult(RespResult.SUCCESS, result);
    }

    /**
     * 获取用户历史记录
     */
    @GetMapping("/history")
    public RespResult history(@RequestParam(required = false) Long userId,
                              @RequestParam(required = false) Long testId) {
        List<QuizResultVO> list = quizService.getHistory(userId, testId);
        return new RespResult(RespResult.SUCCESS, list);
    }
}
