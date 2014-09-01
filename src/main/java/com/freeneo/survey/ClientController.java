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
import org.springframework.web.bind.annotation.ResponseBody;

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
		List<Question> questions = questionMapper.list(survey.getId());
		
		for(Question question : questions){
			List<ResponseItem> responseItems = responseItemMapper.list(question.getId());
			question.setResponseItems(responseItems);
		}
		
		logger.debug("questions = {}", questions);
		
		survey.setQuestions(questions);
		model.addAttribute("survey", survey);
		
		return "client_survey";
	}
}
