package com.freeneo.survey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.domain.UserHistory;
import com.freeneo.survey.mapper.UserMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.service.HistoryService;

@Controller
@RequestMapping(value="history")
public class HistoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);
	
	@Autowired CustomerMapper customerMapper;
	@Autowired UserMapper userMapper;
	@Autowired HistoryService historyService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String history(
			Model model){
		
		List<String> branchList = customerMapper.branchList(null);
		
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
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
			@RequestParam(value="branch") String branch,
			Model model
			){
		
		return "search_by_branch";
	}

	@SuppressWarnings("null")
	@RequestMapping(value="/search-by-user", method=RequestMethod.POST)
	public String searchByUser(
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
			 userHistoryList.add(historyService.getUserHistory(user));
		}
		
		logger.debug("userHistoryList = {}", userHistoryList);
		
		model.addAttribute("userHistoryList", userHistoryList);
		
		return "search_by_user";
	}
}
