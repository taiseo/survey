package com.freeneo.survey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapperInner.TargetMapper;
import com.freeneo.survey.mapper.UserMapper;
import com.freeneo.survey.service.QuestionService;
import com.freeneo.survey.service.StatisticsService;
import com.freeneo.survey.service.SurveyService;
import com.freeneo.survey.util.Util;

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
	@Autowired TargetMapper targetMapper;
	@Autowired StatisticsService statisticsService;
	@Autowired UserMapper userMapper;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String index(
			@PathVariable(value="id") Long id,
			HttpServletRequest request,
			Model model) throws JsonParseException, JsonMappingException, IOException{
		
		// 하나의 설문 전체 통계
		Survey survey = surveyService.getFullSurvey(id);
		statisticsService.setSurveyAllStatistics(survey);
		
		// 하나의 설문에 대한 지사별 통계
		Map<String, Survey> surveyByBranch = statisticsService.getOneSurveyStatisticsByBranches(id);
		
		logger.debug("surveyByBranch = {}", surveyByBranch);
		
		model.addAttribute("survey", survey);
		model.addAttribute("surveyByBranch", surveyByBranch);
        model.addAttribute("pageTitle", survey.getTitle() + " 통계");
        
        String listUrl = request.getContextPath() + "/surveys";
        model.addAttribute("listUrl", Util.getListUrl(request, listUrl));
        
        return "statistics";
    }
	
	@RequestMapping(value="/{branch}/{id}", method=RequestMethod.GET)
	public String byBranch(
			@PathVariable(value="id") Long id,
			@PathVariable(value="branch") String branch,
			HttpServletRequest request,
			Model model) throws JsonParseException, JsonMappingException, IOException{
		
		// 한글 인코딩 처리
		branch = new String(branch.getBytes("8859_1"), "UTF-8");

		Survey survey = statisticsService.getOneSurveyOneBranchStatistics(id, branch);
		
		logger.debug("survey = {}", survey);
		
		model.addAttribute("survey", survey);
        model.addAttribute("pageTitle", branch + " " + survey.getTitle() + " 통계");
        
        String listUrl = request.getContextPath() + "/surveys";
        model.addAttribute("listUrl", Util.getListUrl(request, listUrl));
        
        return "statistics_branch";
    }
	
	@RequestMapping(value="/branch/{branch}/{startDate}/{endDate}", method=RequestMethod.GET)
	public String listByBranch(
			@PathVariable(value="branch") String branch,
			@PathVariable(value="startDate") String startDate,
			@PathVariable(value="endDate") String endDate,
			Model model
			) throws JsonParseException, JsonMappingException, IOException{

		// 한글 인코딩 처리
		branch = new String(branch.getBytes("8859_1"), "UTF-8");
		
		// 하나의 지사, 여러 기간에 걸친 설문들
		List<Survey> surveys = surveyMapper.listByBranchAndDates(branch, startDate, endDate);
		
		for(Survey survey : surveys){
			survey.setRespondentCount(responseMapper
					.countRespondentBySurveyIdAndBranch(survey.getId(), branch));
		}
		
		logger.debug("surveys = {}", surveys);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pageTitle", branch + " 통계");
		model.addAttribute("branch", branch);
		model.addAttribute("surveys", surveys);
		
		return "statistics_branch_list";
	}
	
	@RequestMapping(value="/user/{userId}/{startDate}/{endDate}", method=RequestMethod.GET)
	public String byUser(
			@PathVariable(value="userId") Long userId,
			@PathVariable(value="startDate") String startDate,
			@PathVariable(value="endDate") String endDate,
			Model model
			) throws JsonParseException, JsonMappingException, IOException{
		
		User user = new User();
		user.setId(userId);
		user = userMapper.selectById(user);
		
		// 한 사람이 올린 여러 기간에 걸친 설문들
		List<Survey> surveys = surveyMapper.listByUsernameAndDates(user.getUsername(), startDate, endDate);
		
		for(Survey survey : surveys){
			survey.setRespondentCount(responseMapper
					.countRespondentBySurveyId(survey.getId()));
		}
		
		logger.debug("surveys = {}", surveys);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pageTitle", user.getName() + " 님이 만든 설문들의 통계");
		model.addAttribute("surveys", surveys);
		
		return "statistics_user";
	}
			
}
