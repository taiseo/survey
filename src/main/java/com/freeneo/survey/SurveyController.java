package com.freeneo.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/surveys")
public class SurveyController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("pageTitle", "설문 목록");
		return "survey_list";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insertPage(){
		return "survey_manage";
	}
}
