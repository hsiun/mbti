package com.mbti.modules.quiz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mbti.modules.quiz.dao.*;
import com.mbti.modules.quiz.entity.*;
import com.mbti.modules.quiz.service.QuizService;
import com.mbti.modules.quiz.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用测试服务实现
 * @author: system
 */
@Slf4j
@Service
public class QuizServiceImpl extends ServiceImpl<QuizTestDao, QuizTestEntity> implements QuizService {

    @Autowired
    private QuizQuestionDao questionDao;

    @Autowired
    private QuizOptionDao optionDao;

    @Autowired
    private QuizDimensionDao dimensionDao;

    @Autowired
    private QuizResultRuleDao resultRuleDao;

    @Autowired
    private QuizUserAnswerDao userAnswerDao;

    @Override
    public List<QuizTestEntity> listTests() {
        return list(new LambdaQueryWrapper<QuizTestEntity>()
                .eq(QuizTestEntity::getStatus, "Y")
                .eq(QuizTestEntity::getDelFlag, "N")
                .orderByAsc(QuizTestEntity::getSort));
    }

    @Override
    public QuizTestVO getTestDetail(Long testId) {
        QuizTestEntity test = getById(testId);
        if (test == null) {
            return null;
        }

        QuizTestVO vo = new QuizTestVO();
        BeanUtils.copyProperties(test, vo);

        // 查询题目
        List<QuizQuestionEntity> questions = questionDao.selectList(
                new LambdaQueryWrapper<QuizQuestionEntity>()
                        .eq(QuizQuestionEntity::getTestId, testId)
                        .eq(QuizQuestionEntity::getDelFlag, "N")
                        .orderByAsc(QuizQuestionEntity::getSort));

        // 查询所有选项
        List<Long> questionIds = questions.stream().map(QuizQuestionEntity::getId).collect(Collectors.toList());
        List<QuizOptionEntity> allOptions = optionDao.selectList(
                new LambdaQueryWrapper<QuizOptionEntity>()
                        .in(QuizOptionEntity::getQuestionId, questionIds)
                        .eq(QuizOptionEntity::getDelFlag, "N")
                        .orderByAsc(QuizOptionEntity::getSort));

        // 按题目分组
        Map<Long, List<QuizOptionEntity>> optionMap = allOptions.stream()
                .collect(Collectors.groupingBy(QuizOptionEntity::getQuestionId));

        List<QuizQuestionVO> questionVOs = questions.stream().map(q -> {
            QuizQuestionVO qvo = new QuizQuestionVO();
            BeanUtils.copyProperties(q, qvo);

            List<QuizOptionEntity> opts = optionMap.getOrDefault(q.getId(), Collections.emptyList());
            List<QuizOptionVO> optVOs = opts.stream().map(o -> {
                QuizOptionVO ovo = new QuizOptionVO();
                BeanUtils.copyProperties(o, ovo);
                return ovo;
            }).collect(Collectors.toList());
            qvo.setOptions(optVOs);
            return qvo;
        }).collect(Collectors.toList());

        vo.setQuestions(questionVOs);

        // 查询维度
        List<QuizDimensionEntity> dimensions = dimensionDao.selectList(
                new LambdaQueryWrapper<QuizDimensionEntity>()
                        .eq(QuizDimensionEntity::getTestId, testId)
                        .eq(QuizDimensionEntity::getDelFlag, "N")
                        .orderByAsc(QuizDimensionEntity::getSort));

        List<QuizDimensionVO> dimVOs = dimensions.stream().map(d -> {
            QuizDimensionVO dvo = new QuizDimensionVO();
            BeanUtils.copyProperties(d, dvo);
            return dvo;
        }).collect(Collectors.toList());
        vo.setDimensions(dimVOs);

        return vo;
    }

    @Override
    public QuizResultVO submitAnswer(QuizSubmitDTO submitDTO) {
        QuizTestEntity test = getById(submitDTO.getTestId());
        if (test == null) {
            throw new RuntimeException("测试不存在");
        }

        // 计算分数
        int totalScore = 0;
        Map<String, Integer> dimensionScores = new HashMap<>();

        for (QuizSubmitDTO.AnswerItem item : submitDTO.getAnswers()) {
            // 查询选中的选项
            List<QuizOptionEntity> selectedOptions = optionDao.selectList(
                    new LambdaQueryWrapper<QuizOptionEntity>()
                            .eq(QuizOptionEntity::getQuestionId, item.getQuestionId())
                            .in(QuizOptionEntity::getOptionCode, item.getOptionCodes()));

            for (QuizOptionEntity opt : selectedOptions) {
                totalScore += opt.getScore() != null ? opt.getScore() : 0;

                // 累加维度分数
                if (opt.getDimension() != null && !opt.getDimension().isEmpty()) {
                    int ds = opt.getDimensionScore() != null ? opt.getDimensionScore() : 1;
                    dimensionScores.merge(opt.getDimension(), ds, Integer::sum);
                }
            }
        }

        // 匹配结果规则
        QuizResultRuleEntity matchedRule = matchResultRule(test, totalScore, dimensionScores);

        // 保存用户答题记录
        QuizUserAnswerEntity record = new QuizUserAnswerEntity();
        record.setTestId(submitDTO.getTestId());
        record.setUserId(submitDTO.getUserId());
        record.setSessionId(submitDTO.getSessionId());
        record.setAnswerData(JSON.toJSONString(submitDTO.getAnswers()));
        record.setTotalScore(totalScore);
        record.setDimensionScores(JSON.toJSONString(dimensionScores));
        record.setTimeSpent(submitDTO.getTimeSpent());
        record.setCreateTime(new Date());

        if (matchedRule != null) {
            record.setResultCode(matchedRule.getResultCode());
            record.setResultTitle(matchedRule.getResultTitle());
            
            QuizResultVO resultVO = new QuizResultVO();
            resultVO.setResultCode(matchedRule.getResultCode());
            resultVO.setResultTitle(matchedRule.getResultTitle());
            resultVO.setDescription(matchedRule.getDescription());
            resultVO.setSuggestion(matchedRule.getSuggestion());
            resultVO.setImageUrl(matchedRule.getImageUrl());
            resultVO.setTotalScore(totalScore);
            resultVO.setDimensionScores(dimensionScores);
            
            record.setResultData(JSON.toJSONString(resultVO));
            userAnswerDao.insert(record);
            
            resultVO.setRecordId(record.getId());
            resultVO.setTestId(test.getId());
            return resultVO;
        }

        // 无匹配规则时返回原始分数
        QuizResultVO resultVO = new QuizResultVO();
        resultVO.setTotalScore(totalScore);
        resultVO.setDimensionScores(dimensionScores);
        
        userAnswerDao.insert(record);
        resultVO.setRecordId(record.getId());
        resultVO.setTestId(test.getId());
        return resultVO;
    }

    /**
     * 匹配结果规则
     */
    private QuizResultRuleEntity matchResultRule(QuizTestEntity test, int totalScore, Map<String, Integer> dimensionScores) {
        List<QuizResultRuleEntity> rules = resultRuleDao.selectList(
                new LambdaQueryWrapper<QuizResultRuleEntity>()
                        .eq(QuizResultRuleEntity::getTestId, test.getId())
                        .eq(QuizResultRuleEntity::getDelFlag, "N")
                        .orderByDesc(QuizResultRuleEntity::getPriority));

        if ("SCORE".equals(test.getResultType())) {
            // 分数型：根据分数区间匹配
            for (QuizResultRuleEntity rule : rules) {
                if (rule.getMinScore() != null && rule.getMaxScore() != null) {
                    if (totalScore >= rule.getMinScore() && totalScore <= rule.getMaxScore()) {
                        return rule;
                    }
                }
            }
        } else if ("TYPE".equals(test.getResultType())) {
            // 类型型：根据维度分数组合匹配
            for (QuizResultRuleEntity rule : rules) {
                if (rule.getDimensionValues() != null) {
                    // 简单匹配逻辑：检查维度组合
                    // 实际可根据需要扩展更复杂的匹配逻辑
                    return rule;
                }
            }
        }

        return rules.isEmpty() ? null : rules.get(0);
    }

    @Override
    public List<QuizResultVO> getHistory(Long userId, Long testId) {
        List<QuizUserAnswerEntity> records = userAnswerDao.selectList(
                new LambdaQueryWrapper<QuizUserAnswerEntity>()
                        .eq(userId != null, QuizUserAnswerEntity::getUserId, userId)
                        .eq(testId != null, QuizUserAnswerEntity::getTestId, testId)
                        .eq(QuizUserAnswerEntity::getDelFlag, "N")
                        .orderByDesc(QuizUserAnswerEntity::getCreateTime));

        return records.stream().map(r -> {
            QuizResultVO vo = new QuizResultVO();
            vo.setRecordId(r.getId());
            vo.setTestId(r.getTestId());
            vo.setTotalScore(r.getTotalScore());
            vo.setResultCode(r.getResultCode());
            vo.setResultTitle(r.getResultTitle());
            if (r.getDimensionScores() != null) {
                vo.setDimensionScores(JSON.parseObject(r.getDimensionScores(), Map.class));
            }
            return vo;
        }).collect(Collectors.toList());
    }
}
