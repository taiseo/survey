package com.freeneo.survey;

import java.util.List;

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

@Controller
@RequestMapping(value="/survey")
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	SurveyMapper surveyMapper;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	ResponseItemMapper responseItemMapper;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String get(
			@PathVariable(value="id") Long id,
			Model model
			){
		
		Survey survey = surveyMapper.select(id);
		
		// TODO 설문 종료일 체크
		
		List<Question> questions = questionMapper.list(survey.getId());

		int pageBreakerCount = 0;
		for(Question question : questions){
			List<ResponseItem> responseItems = responseItemMapper.list(question.getId());
			if(question.getType().equals("점수범위")){
				responseItems.get(0).setMinMax();
			}
			question.setResponseItems(responseItems);
			if(question.getContent().equals("$$$pageBreaker$$$")){
				pageBreakerCount++;
			}
		}
		
		survey.setQuestions(questions);
		
		logger.debug("survey = {}", survey);
		
		model.addAttribute("pages", pageBreakerCount+1);
		model.addAttribute("pageTitle", survey.getTitle());
		model.addAttribute("isClient", true);
		model.addAttribute("survey", survey);
		
		return "client_survey";
	}
}
