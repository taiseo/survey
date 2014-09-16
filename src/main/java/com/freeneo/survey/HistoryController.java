package com.freeneo.survey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.UserMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;

@Controller
@RequestMapping(value="history")
public class HistoryController {
	
	@Autowired CustomerMapper customerMapper;
	@Autowired UserMapper userMapper;
	
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
}
