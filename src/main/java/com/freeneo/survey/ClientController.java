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
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.service.SurveyService;
import com.freeneo.survey.util.Util;

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
		
		String endDate = survey.getEndDate();

		int eYear = new Integer(endDate.substring(0, 4)).intValue();
		int eMonth = new Integer(endDate.substring(5, 7)).intValue();
		int eDay = new Integer(endDate.substring(8, 10)).intValue();

		int tYear = new Integer(Util.systemDate("yyyy"));
		int tMonth = new Integer(Util.systemDate("MM"));
		int tDay = new Integer(Util.systemDate("dd"));

		
		if(tYear >= eYear && tMonth >= eMonth && tDay > eDay){
			
			logger.debug("설문날짜 초과!!!");
			
			model.addAttribute("pageTitle", "설문기간 종료");
			model.addAttribute("error_msg",
					"설문기간이 종료되었습니다.");
			
			return "client_end";
		}
		
		
		return "client_survey";
	}
}
