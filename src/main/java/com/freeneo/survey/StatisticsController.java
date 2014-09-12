package com.freeneo.survey;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/statistics")
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	
	@Autowired
	ResponseMapper responseMapper;
	
	@Autowired
	SurveyMapper surveyMapper;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String index(
			@PathVariable(value="id") Long id,
			Model model){
		
		Survey survey = surveyMapper.select(id);
		survey.setRespondentCount(surveyMapper.selectRespondentCount(id));
		List<Question> questions = questionMapper.list(survey.getId());
		
		
		model.addAttribute("survey", survey);
		model.addAttribute("questions", questions);
        model.addAttribute("pageTitle", "통계");
        
//        responseMapper.

        return "statistics";
    }
}
