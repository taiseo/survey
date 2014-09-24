package com.freeneo.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeneo.survey.domain.SVLog;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.SVLogMapper;

@Controller
@RequestMapping(value="/logs")
public class SVLogController {
	
	@Autowired SVLogMapper svlogMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model){
		return list("1", model);		
	}
	
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)	
	public String list(
			@PathVariable(value = "page") String page,
			Model model){
		
		List<SVLog> logs = svlogMapper.list();
		
		PagedListHolder<SVLog> pagedListHolder = new PagedListHolder<SVLog>(
				logs);
		pagedListHolder.setPageSize(10);

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
		
		return "sv_logs";
	}
	
}
