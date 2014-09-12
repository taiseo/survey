package com.freeneo.survey;

import com.freeneo.survey.domain.Response;
import com.freeneo.survey.mapper.ResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/statistics")
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	
	@Autowired
	ResponseMapper responseMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
        model.addAttribute("pageTitle", "통계");

        return "statistics";
    }
}
