package com.freeneo.survey.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;

@Service
public class SurveyService {
	
	static Logger logger = LoggerFactory.getLogger(SurveyService.class);
	
	@Autowired
	SurveyMapper surveyMapper;
	
	@Autowired QuestionMapper questionMapper;
	@Autowired ResponseItemMapper responseItemMapper;
	
	public Survey getFullSurvey(Long id){
		Survey survey = surveyMapper.select(id);
		List<Question> questions = questionMapper.list(survey.getId());
		for(Question question : questions){
			List<ResponseItem> responseItems = responseItemMapper.list(question.getId());
			if(question.getType().equals("점수범위")){
				responseItems.get(0).setMinMax();
			}
			question.setResponseItems(responseItems);
		}
		survey.setQuestions(questions);
		
		return survey;
	}
}
