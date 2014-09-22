package com.freeneo.survey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.ConfigMapper;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetGroupMapper;
import com.freeneo.survey.mapper.TargetMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.mapperMms.MmsMapper;

@Service
public class StatisticsService {

	static Logger logger = LoggerFactory.getLogger(StatisticsService.class);

	@Autowired
	SurveyMapper surveyMapper;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	ResponseItemMapper responseItemMapper;
	@Autowired
	TargetMapper targetMapper;
	@Autowired
	MmsMapper mmsMapper;
	@Autowired
	TargetGroupMapper targetGroupMapper;
	@Autowired
	ConfigMapper configMapper;
	@Autowired
	ResponseMapper responseMapper;
	@Autowired
	QuestionService questionService;
	@Autowired
	SurveyService surveyService;
	

	/**
	 * 한 설문 전체 기준 통계
	 * @param survey
	 */
	public void setSurveyAllStatistics(Survey survey) {
		
		survey.setRespondentCount(responseMapper.countRespondentBySurveyId(survey.getId()));
		
		if(survey.getQuestions() != null){
			
			for(Question question : survey.getQuestions()){
				
				question.setQuestionRespondentCount(questionMapper.selectRespondentCount(question.getId()));
				
				if(question.getType().contains("주관식")){
					question.setResponses(questionMapper.selectResponses(question.getId()));
				}else if(question.getType().equals("점수범위")){
					question.setResponses(questionMapper.selectResponses(question.getId()));
					questionService.setPointResponseCount(question);
				}else{
					// 나머지는 객관식
					for(ResponseItem responseItem : question.getResponseItems()){
						responseItem.setResponseItemCount(responseItemMapper.selectResponseItemCount(responseItem));
					}
					questionService.setEtcResponses(question);
				}
			}
		}
		
	}


	/**
	 * 한 설문에 대한 지사별 통계
	 * @param surveyId
	 * @return
	 */
	public Map<String, Survey> getOneSurveyStatisticsByBranches(Long surveyId) {
		
		List<Response> fullResponses = responseMapper.listFullBySurveyId(surveyId);
		List<String> branches = targetMapper.branchListBySurveyId(surveyId);
		
		logger.debug("fullResponses = {}", fullResponses);
		logger.debug("branches = {}", branches);
		
		Map<String, Survey> surveyByBranch = new HashMap<String, Survey>();
		for(String branch : branches){
			Survey tempSurvey = getOneSurveyOneBranchStatistics(surveyId, branch);
			surveyByBranch.put(branch, tempSurvey);
		}
		
		return surveyByBranch;
	}


	/***
	 * 한 지사의 한 설문 통계 세팅
	 * @param surveyId
	 * @param branch
	 * @return
	 */
	public Survey getOneSurveyOneBranchStatistics(Long surveyId, String branch) {
		Survey survey = surveyService.getFullSurvey(surveyId);
		survey.setRespondentCount(responseMapper.countRespondentBySurveyIdAndBranch(surveyId, branch));
		for(Question question : survey.getQuestions()){
			question.setQuestionRespondentCount(questionMapper.selectRespondentCountByBranch(question.getId(), branch));
			
			if(question.getType().contains("주관식")){
				question.setResponses(questionMapper.selectResponsesByBranch(question.getId(), branch));
			}else if(question.getType().equals("점수범위")){
				question.setResponses(questionMapper.selectResponsesByBranch(question.getId(), branch));
				questionService.setPointResponseCount(question);
			}else{
				// 나머지는 객관식
				for(ResponseItem responseItem : question.getResponseItems()){
					responseItem.setResponseItemCount(responseItemMapper.selectResponseItemCountByBranch(responseItem, branch));
				}
				questionService.setEtcResponsesByBranch(question, branch);
			}
		}
		return survey;
	}

	
}
