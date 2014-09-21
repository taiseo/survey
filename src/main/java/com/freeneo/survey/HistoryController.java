package com.freeneo.survey;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.freeneo.survey.domain.BranchHistory;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.Target;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.domain.UserHistory;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetMapper;
import com.freeneo.survey.mapper.UserMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.service.HistoryService;
import com.freeneo.survey.service.SurveyService;

@Controller
@RequestMapping(value="/history")
public class HistoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);
	
	@Autowired CustomerMapper customerMapper;
	@Autowired UserMapper userMapper;
	@Autowired HistoryService historyService;
	@Autowired SurveyMapper surveyMapper;
	@Autowired TargetMapper targetMapper;
	@Autowired SurveyService surveyService;
	@Autowired ResponseMapper responseMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String history(Model model){
		
		List<String> branchList = customerMapper.branchList(null);
		
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		List<User> userList = userMapper.list();
		List<String> partList = userMapper.partList();
		
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("branchList", branchList);
		model.addAttribute("partList", partList);
		model.addAttribute("userList", userList);
		model.addAttribute("pageTitle", "운영 및 통계 내역");
		
		return "history";
	}
	
	@RequestMapping(value="/search-by-branch", method=RequestMethod.POST)
	public String searchByBranch(
			@RequestParam(value="startDate", required=false, defaultValue="") String startDate,
			@RequestParam(value="endDate", required=false, defaultValue="") String endDate,
			@RequestParam(value="branches", required=false, defaultValue="") String branches,
			Model model
			) throws JsonParseException, JsonMappingException, IOException{
		
		// 날짜 기반으로 survey를 고른다.
		List<Survey> surveyList = surveyMapper.selectByDate(startDate, endDate);
		
		logger.debug("surveyList = {}", surveyList);
		
		// branches를 List<String>으로 바꾼다.
		List<String> branchList = surveyService.makeBranchList(branches);
		
		logger.debug("branchList = {}", branchList);
		
		List<BranchHistory> branchHistoryList = new ArrayList<BranchHistory>();
		
		List<Target> targetList = targetMapper.listBySurveysAndBranches(surveyList, branchList);
		
		logger.debug("targetList = {}", targetList);
		
		List<Map<String, ?>> countByBranch = responseMapper.countRespondentBySurveysAndBranches(surveyList, branchList);
		
		logger.debug("countByBranch = {}", countByBranch);
		
		int allRespondentCount = 0;
		
		for(String branch : branchList){
			BranchHistory branchHistory = new BranchHistory();
			branchHistory.setBranchName(branch);
			
			int sendCount = 0;
			int respondentCount = getRespondentCount(countByBranch, branch);;
			double responseRatio = 0;
			
			allRespondentCount += respondentCount;
			
			for(Target target : targetList){
				if(target.getEtc01().equals(branch)){
					sendCount++;
				}
			}
			
			if(sendCount != 0){
				responseRatio = (double) respondentCount / (double) sendCount * 100.0;
			}
			
			branchHistory.setSendCount(sendCount);
			branchHistory.setRespondentCount(respondentCount);
			branchHistory.setResponseRatio(responseRatio);

			branchHistoryList.add(branchHistory);
		}
		
		logger.debug("branchHistoryList = {}", branchHistoryList);
		
		double allResponseRatio = 0;
		if(targetList.size() != 0){
			allResponseRatio = (double) allRespondentCount / (double) targetList.size() * 100.0;
		}
		
		model.addAttribute("branchHistoryList", branchHistoryList);
		model.addAttribute("allSendCount", targetList.size());
		model.addAttribute("allRespondentCount", allRespondentCount);
		model.addAttribute("allResponseRatio", allResponseRatio);

		return "search_by_branch";
	}

	private int getRespondentCount(List<Map<String, ?>> countByBranch,
			String branch) {

		int count = 0;
		
		for(Map<String, ?> branchCount : countByBranch){
			if(branchCount.get("BRANCH").equals(branch)){
				count = Integer.valueOf(((BigDecimal) branchCount.get("COUNT")).intValue());
			}
		}
		
		return count;
	}

	@RequestMapping(value="/search-by-user", method=RequestMethod.POST)
	public String searchByUser(
			@RequestParam(value="startDate", required=false, defaultValue="") String startDate,
			@RequestParam(value="endDate", required=false, defaultValue="") String endDate,
			@RequestParam(value="part") String part,
			@RequestParam(value="username") String username,
			Model model
			){
		
		List<User> users = new ArrayList<User>();
		if( ! username.equals("")){
			User user = new User();
			user.setUsername(username);
			user = userMapper.selectByUsername(user);
			if(user != null){
				users.add(user);
			}
		}else if( ! part.equals("")){
			users = userMapper.listByPart(part);
		}else{
			// 둘 다 빈 값인 경우다.
			users = userMapper.list();
		}
		
		List<UserHistory> userHistoryList = new ArrayList<UserHistory>();
		for(User user : users){
			userHistoryList.add(historyService.getUserHistory(user, startDate, endDate));
		}
		
		logger.debug("userHistoryList = {}", userHistoryList);
		
		model.addAttribute("userHistoryList", userHistoryList);
		
		return "search_by_user";
	}
}
