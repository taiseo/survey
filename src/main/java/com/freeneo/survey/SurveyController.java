package com.freeneo.survey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.SurveyMapper;

@Controller
@RequestMapping(value="/surveys")
public class SurveyController {
	
	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	SurveyMapper surveyMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		
		List<Survey> list = surveyMapper.list();
		
		model.addAttribute("list", list);
		model.addAttribute("pageTitle", "설문 목록");
		return "survey_list";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insertPage(Model model){
		Survey survey = new Survey();

		survey.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +7);
		survey.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		
		model.addAttribute("pageTitle", "새 설문");
		model.addAttribute("survey", survey);
		model.addAttribute("httpMethod", "POST");
		return "survey_manage";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insertAction(
			@RequestParam(value="title") String title,
			@RequestParam(value="description") String description,
			@RequestParam(value="startDate") String startDate,
			@RequestParam(value="endDate") String endDate,
			@RequestParam(value="target") String target,
			Model model,
			HttpSession session
			){
		
		if(title.equals("") || endDate.equals("")){
			model.addAttribute("error_msg", "필수 항목(제목, 게시종료일)을 모두 입력해 주세요.");
			return insertPage(model);
		}
		
		Survey survey = new Survey();
		survey.setTitle(title);
		survey.setDescription(description);
		survey.setStartDate(startDate);
		survey.setEndDate(endDate);
		survey.setTarget(target);
		
		User currentUser = (User) session.getAttribute("user");
		survey.setWriter(currentUser.getUsername());
		survey.setPart(currentUser.getPart());
		
		logger.debug("survey = {}", survey);
		
		surveyMapper.insert(survey);
		
		return "redirect:/surveys";
	}
}
