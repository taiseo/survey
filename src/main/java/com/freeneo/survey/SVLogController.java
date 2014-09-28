package com.freeneo.survey;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.SVLog;
import com.freeneo.survey.mapper.SVLogMapper;

@Controller
@RequestMapping(value="/logs")
public class SVLogController {
	
	private static final Logger logger = LoggerFactory.getLogger(SVLogController.class);
	
	@Autowired SVLogMapper svlogMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(
			Model model,
			@RequestParam(value = "isExcel", required = false, defaultValue = "") String isExcel
			){
		return list("1", model, isExcel);		
	}
	
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)	
	public String list(
			@PathVariable(value = "page") String page,
			Model model,
			String isExcel
			){
		
		logger.debug("isExcel = {}", isExcel);
		
		List<SVLog> logs = svlogMapper.list();
		
		PagedListHolder<SVLog> pagedListHolder = new PagedListHolder<SVLog>(
				logs);
		if(isExcel.equals("Y")){
			pagedListHolder.setPageSize(999999);
		}else{
			pagedListHolder.setPageSize(10);
		}

		if (page.equalsIgnoreCase("next")) {
			pagedListHolder.nextPage();
		} else if (page.equalsIgnoreCase("prev")) {
			pagedListHolder.previousPage();
		} else if (page.equalsIgnoreCase("first")) {
			pagedListHolder.setPage(0);
		} else if (page.equalsIgnoreCase("last")) {
			pagedListHolder.setPage(pagedListHolder.getPageCount());
		} else {
			pagedListHolder.setPage(Integer.parseInt(page) - 1);
		}

		
		model.addAttribute("pageTitle", "접속 기록 관리");
		model.addAttribute("pagedListHolder", pagedListHolder);
		
		if(isExcel.equals("Y")){
			return "sv_logs_excel";
		}else{
			return "sv_logs";
		}
	}
	
}
