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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.service.SurveyService;

@Controller
@RequestMapping(value="/surveys")
public class SurveyController {
	
	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	SurveyMapper surveyMapper;

	@Autowired
	SurveyService surveyService;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	ResponseItemMapper responseItemMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		
		List<Survey> list = surveyMapper.list();
		
		logger.debug("surveys = {}", list);
		
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
		
		logger.debug("insertedSurvey = {}", survey);
		
		return "redirect:/surveys/detail/" + survey.getId();
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String updatePage(
			@PathVariable(value="id") Long id,
			Model model,
			HttpSession session,
			Survey survey
			){

		User currentUser = (User) session.getAttribute("user");
		logger.debug("survey={}" ,survey);
		if(survey.getTitle() == null){
			logger.debug("DB에 있는 survey를 가져옴.");
			survey = surveyMapper.select(id);
			logger.debug("survey={}" ,survey);
		}
		
		// admin이 아닌데, 남의 것을 수정하려고 하면
		logger.debug("currentUser.userLevel={}", currentUser.getUserLevel());
		logger.debug("currentUser.username={}", currentUser.getUsername());
		logger.debug("survey.writer={}", survey.getWriter());
		if( ! currentUser.getUserLevel().equals("admin") && ! currentUser.getUsername().equals(survey.getWriter())){
			model.addAttribute("error_msg", "남의 것은 수정할 수 없습니다.");
			return list(model);
		}
		
		model.addAttribute("pageTitle", survey.getTitle() + " 수정");
		model.addAttribute("survey", survey);
		model.addAttribute("httpMethod", "PUT");
		return "survey_manage";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public String updateAction(
			@PathVariable(value="id") Long id,
			@RequestParam(value="title") String title,
			@RequestParam(value="description") String description,
			@RequestParam(value="startDate") String startDate,
			@RequestParam(value="endDate") String endDate,
			@RequestParam(value="target") String target,
			Model model,
			HttpSession session
			){

		User currentUser = (User) session.getAttribute("user");
		Survey oldSurvey = surveyMapper.select(id);
		
		// admin이 아닌데, 남의 것을 수정하려고 하면
		if( ! currentUser.getUserLevel().equals("admin") && ! currentUser.getUsername().equals(oldSurvey.getWriter())){
			model.addAttribute("error_msg", "남의 것은 수정할 수 없습니다.");
			return list(model);
		}
		
		Survey newSurvey = new Survey();
		newSurvey.setId(id);
		newSurvey.setTitle(title);
		newSurvey.setDescription(description);
		newSurvey.setStartDate(startDate);
		newSurvey.setEndDate(endDate);
		newSurvey.setTarget(target);
		newSurvey.setWriter(oldSurvey.getWriter());
		newSurvey.setPart(oldSurvey.getPart());
		
		if(title.equals("") || endDate.equals("")){
			model.addAttribute("error_msg", "필수 항목(제목, 게시종료일)을 모두 입력해 주세요.");
			logger.debug("필수항목을 빠뜨린 경우. 이전 입력 정보를 들고 업데이트페이지로 감.");
			return updatePage(id, model, session, newSurvey);
		}
		
		logger.debug("oldSurvey = {}", oldSurvey);
		logger.debug("newSurvey = {}", newSurvey);
		
		surveyMapper.update(newSurvey);
		
		return "redirect:/surveys/detail/" + id;
	}
	
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	public String detailPage(
			@PathVariable(value="id") Long id,
			Model model, 
			HttpSession session
			){
		
		User currentUser = (User) session.getAttribute("user");
		Survey survey = surveyMapper.select(id);
		if( ! currentUser.getUserLevel().equals("admin") && ! currentUser.getUsername().equals(survey.getWriter())){
			model.addAttribute("error_msg", "남의 것을 편집할 수는 없습니다.");
			return list(model);
		}
	
		List<Question> questions = questionMapper.list(id);
		
		for(Question question : questions){
			List<ResponseItem> responseItems = responseItemMapper.list(question.getId());
			question.setResponseItems(responseItems);
		}
		
		logger.debug("questions = {}", questions);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String questionsString = null;
		try {
			questionsString = objectMapper.writeValueAsString(questions);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		logger.debug("questionsString = {}", questionsString);
		
		model.addAttribute("survey", survey);
		model.addAttribute("questionsString", questionsString);
		model.addAttribute("pageTitle", survey.getTitle() + " 문항 편집");
		return "survey_detail";
	}
	
	@RequestMapping(value="/status/{id}", method=RequestMethod.GET)
	public String status(
			@PathVariable(value="id") Long id,
			Model model
			){
		Survey survey = surveyService.getFullSurvey(id);
		model.addAttribute("survey", survey);
		
		
		return "survey_status";
	}
}
