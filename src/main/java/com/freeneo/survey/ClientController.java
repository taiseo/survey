package com.freeneo.survey;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.service.SurveyService;

@Controller
@RequestMapping(value="/survey")
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	SurveyMapper surveyMapper;
	
	@Autowired
	SurveyService surveyService;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	ResponseItemMapper responseItemMapper;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String get(
			@PathVariable(value="id") Long id,
			Model model,
			HttpSession session
			){
		
		
		// TODO 설문 종료일 체크
		
		Survey survey = surveyService.getFullSurvey(id);
		
		List<Question> questions = survey.getQuestions();
		int pageBreakerCount = 0;
		for(Question question : questions){
			if(question.getType().equals("페이지-나누기")){
				pageBreakerCount++;
			}
		}
		
		logger.debug("survey = {}", survey);
		
		model.addAttribute("sessionId", session.getId());
		model.addAttribute("pages", pageBreakerCount+1);
		model.addAttribute("pageTitle", survey.getTitle());
		model.addAttribute("isClient", true);
		model.addAttribute("survey", survey);
		
		return "client_survey";
	}
}
