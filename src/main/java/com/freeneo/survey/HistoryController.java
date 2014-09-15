package com.freeneo.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeneo.survey.mapperCrm.CustomerMapper;

@Controller
@RequestMapping(value="history")
public class HistoryController {
	
	@Autowired CustomerMapper customerMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String history(
			Model model){
		
		List<String> branchList = customerMapper.branchList();
		
		model.addAttribute("branchList", branchList);
		model.addAttribute("pageTitle", "운영 및 통계 내역");
		
		return "history";
	}
}
