package com.freeneo.survey;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetMapper;
import com.freeneo.survey.service.QuestionService;
import com.freeneo.survey.service.StatisticsService;
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
	@Autowired TargetMapper targetMapper;
	@Autowired StatisticsService statisticsService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String index(
			@PathVariable(value="id") Long id,
			Model model){
		
		// 하나의 설문 전체 통계
		Survey survey = surveyService.getFullSurvey(id);
		statisticsService.setSurveyAllStatistics(survey);
		
		// 하나의 설문에 대한 지사별 통계
		Map<String, Survey> surveyByBranch = statisticsService.getOneSurveyStatisticsByBranches(id);
		
		logger.debug("surveyByBranch = {}", surveyByBranch);
		
		model.addAttribute("survey", survey);
		model.addAttribute("surveyByBranch", surveyByBranch);
        model.addAttribute("pageTitle", survey.getTitle() + " 통계");
        
        return "statistics";
    }
	
	@RequestMapping(value="/branch/{branch}/{startDate}/{endDate}", method=RequestMethod.GET)
	public String byBranch(
			@PathVariable(value="branch") String branch,
			@PathVariable(value="startDate") String startDate,
			@PathVariable(value="endDate") String endDate,
			Model model
			) throws UnsupportedEncodingException{

		// 한글 인코딩 처리
		branch = new String(branch.getBytes("8859_1"), "UTF-8");
		
		// 하나의 지사, 여러 기간에 걸친 설문들
		List<Survey> surveys = surveyMapper.listByBranchAndDates(branch, startDate, endDate);
		List<Survey> statisticsSurveys = new ArrayList<Survey>();
		
		for(Survey survey : surveys){
			statisticsSurveys.add(statisticsService.getOneSurveyOneBranchStatistics(survey.getId(), branch));
		}
		
		logger.debug("statisticsSurveys = {}", statisticsSurveys);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pageTitle", branch + " 통계");
		model.addAttribute("statisticsSurveys", statisticsSurveys);
		
		return "statistics_branch";
	}
}
