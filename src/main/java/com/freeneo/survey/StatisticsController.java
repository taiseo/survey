package com.freeneo.survey;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.service.QuestionService;
import com.freeneo.survey.service.SurveyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/statistics")
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	
	@Autowired ResponseMapper responseMapper;
	@Autowired ResponseItemMapper responseItemMapper;
	@Autowired SurveyMapper surveyMapper;
	@Autowired SurveyService surveyService;
	@Autowired QuestionMapper questionMapper;
	@Autowired QuestionService questionService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String index(
			@PathVariable(value="id") Long id,
			Model model){
		
		Survey survey = surveyService.getFullSurvey(id);
		survey.setRespondentCount(surveyMapper.selectRespondentCount(id));
		
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
		
		logger.debug("survey = {}", survey);
		
		model.addAttribute("survey", survey);
        model.addAttribute("pageTitle", "통계");
        
        return "statistics";
    }
}
