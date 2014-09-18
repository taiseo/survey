package com.freeneo.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeneo.survey.domain.SVLog;
import com.freeneo.survey.mapper.SVLogMapper;

@Controller
@RequestMapping(value="/logs")
public class SVLogController {
	
	@Autowired SVLogMapper svlogMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model){
		
		List<SVLog> logs = svlogMapper.list();
		
		model.addAttribute("pageTitle", "접속 기록 관리");
		model.addAttribute("logs", logs);
		
		return "sv_logs";
	}
	
}
