package com.freeneo.survey;

import java.text.ParseException;
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

import com.freeneo.survey.domain.ConfigItem;
import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.ConfigMapper;
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
	
	@Autowired
	ConfigMapper configMapper;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String get(
			@PathVariable(value="id") Long id,
			Model model,
			HttpSession session
			) throws ParseException{
		
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
		
		if(Util.compareWithToday(survey.getEndDate()) > 0){
			
			logger.debug("설문날짜 초과!!!");
			
			model.addAttribute("pageTitle", survey.getTitle() + " 설문기간 종료");
			model.addAttribute("error_msg", "설문기간이 종료되었습니다.");
			
			User user = (User) session.getAttribute("user");
			
			if(user == null){
				return "client_end";
			}else{
				model.addAttribute("error_msg", "설문기간이 종료되었습니다. 관리자기 때문에 설문 내용은 보여 드립니다. 일반 사용자에겐 설문 내용도 보여 주지 않습니다.");
			}
		}
		
		if(Util.compareWithToday(survey.getStartDate()) < 0){
			
			logger.debug("설문날짜 이전!!!");
			
			model.addAttribute("pageTitle", survey.getTitle() + " 설문시작 전");
			model.addAttribute("error_msg", "설문기간 시작 전입니다.");
			
			User user = (User) session.getAttribute("user");
			
			if(user == null){
				return "client_end";
			}else{
				model.addAttribute("error_msg", "설문기간 시작 전입니다. 관리자기 때문에 설문 내용은 보여 드립니다. 일반 사용자에겐 설문 내용도 보여 주지 않습니다.");
			}
		}
		
		ConfigItem organization = configMapper.select("organization");
		ConfigItem phone = configMapper.select("phone");
		ConfigItem fax = configMapper.select("fax");
		ConfigItem address = configMapper.select("address");
		ConfigItem logo_image = configMapper.select("logo_image");
		ConfigItem domain = configMapper.select("domain");

		model.addAttribute("organization", ( organization != null ? organization.getValue() : "" ) );
		model.addAttribute("phone", ( phone != null ? phone.getValue() : "" ) );
		model.addAttribute("fax", ( fax != null ? fax.getValue() : "" ) );
		model.addAttribute("address", ( address != null ? address.getValue() : "" ) );
		model.addAttribute("logo_image", ( logo_image != null ? logo_image.getValue() : "" ) );
		model.addAttribute("domain_value", ( domain != null ? domain.getValue() : "" ) );		
		
		return "client_survey";
	}
}
