package com.freeneo.survey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.TargetGroup;
import com.freeneo.survey.mapper.TargetGroupMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.service.SurveyService;

@RequestMapping(value="/target-groups")
@Controller
public class TargetGroupController {
	private static final Logger logger = LoggerFactory.getLogger(TargetGroupController.class);
	
	@Autowired CustomerMapper customerMapper;
	@Autowired TargetGroupMapper targetGroupMapper;
	@Autowired SurveyService surveyService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		return list("1", model);		
	}
	
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String list(
			@PathVariable(value = "page") String page,
			Model model){
		
		List<TargetGroup> targetGroups = targetGroupMapper.list();
		
		logger.debug("targetGroups = {}", targetGroups);
		
		PagedListHolder<TargetGroup> pagedListHolder = new PagedListHolder<TargetGroup>(
				targetGroups);
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
		
		model.addAttribute("pageTitle", "캠페인(타겟) 그룹 목록");
		model.addAttribute("targetGroups", targetGroups);
		model.addAttribute("pagedListHolder", pagedListHolder);
		
		return "target_group_list";
	}
	
	@RequestMapping(value="/branches", method=RequestMethod.POST)
	@ResponseBody
	public List<String> getBranches(
			@RequestParam(value="targetGroupIds") String targetGroupIds
			) throws JsonParseException, JsonMappingException, IOException{
		
		return surveyService.getBranchesByTargetGroupIds(targetGroupIds);
	}
	
	@RequestMapping(value="/insert", method = RequestMethod.GET)
	public String insertPage(Model model){
		
		TargetGroup targetGroup = new TargetGroup();
		
		model.addAttribute("httpMethod", "POST");
		model.addAttribute("pageTitle", "새 캠페인(타겟) 그룹");
		model.addAttribute("targetGroup", targetGroup);
		
		return "target_group_manage";
	}
	
	@RequestMapping(value="/update/{id}", method = RequestMethod.GET)
	public String updatePage(
			@PathVariable(value="id") Long id,
			Model model){
		
		TargetGroup targetGroup = targetGroupMapper.select(id);
		
		logger.debug("targetGroup = {}", targetGroup);

		model.addAttribute("targetBranches", targetGroup.getBranches());
		model.addAttribute("httpMethod", "PUT");
		model.addAttribute("pageTitle", "새 캠페인(타겟) 그룹");
		model.addAttribute("targetGroup", targetGroup);
		
		return "target_group_manage";
	}

	
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public String insertAction(
			TargetGroup targetGroup,
			Model model){
		
		logger.debug("targetGroup = {}", targetGroup);
		
		targetGroupMapper.insert(targetGroup);
		
		return "redirect:/target-groups";
	}
	
	@RequestMapping(value="/update/{id}", method = RequestMethod.PUT)
	public String updateAction(
			TargetGroup targetGroup,
			Model model){
		
		logger.debug("targetGroup = {}", targetGroup);
		
		targetGroupMapper.update(targetGroup);
		
		return "redirect:/target-groups";
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String deleteAction(@PathVariable(value="id") Long id){
		
		logger.debug("id = {}", id);
		
		targetGroupMapper.delete(id);
		
		return "redirect:/target-groups";
	}
		
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	@ResponseBody
	public String targetGroupCount(
			@RequestParam(value = "targetGroupIds") String targetGroupIds,
			@RequestParam(value = "startDate") String startDate, 
			@RequestParam(value = "endDate") String endDate, 
			HttpServletRequest request,
			Model model)
			throws JsonParseException, JsonMappingException, IOException {

		logger.debug("targetGroupIds = {}", targetGroupIds);
		
		List<Long> targetGroupIdList = surveyService.makeSelectedTargetGroups(targetGroupIds);

		int count = 0;
		for( long id : targetGroupIdList ){
			count += getCount(id, startDate, endDate);
		}

		String linkStr = "";
		
		if(count > 0){
			linkStr = "<a href='#' class='js-target-group-detail'>" + count + "명 <small>(자세히 보기)</small></a>";
		}else{
			linkStr = "0명";
		}
		
		return linkStr;
	}

	private int getCount(long id, String startDate, String endDate)
			throws JsonParseException, JsonMappingException, IOException {
		TargetGroup tg = targetGroupMapper.select(id);
		List<Customer> customers = surveyService.customerList(tg.getCategory1(), tg.getCategory2(), tg.getBranches(), tg.getLimit(), startDate, endDate);
		return customers.size();
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public String targetDetail(
			@RequestParam(value = "targetGroupIds") String targetGroupIds,
			@RequestParam(value = "startDate") String startDate, 
			@RequestParam(value = "endDate") String endDate, 
			HttpServletRequest request,
			Model model)
			throws JsonParseException, JsonMappingException, IOException {
		
		logger.debug("targetGroupIds = {}", targetGroupIds);
		
		List<Long> targetGroupIdList = surveyService.makeSelectedTargetGroups(targetGroupIds);
		List<Map<String, String>> targetInfosByTargetGroup = new ArrayList<Map<String, String>>();
		
		for( long id : targetGroupIdList ){
			
			Map<String, String> targetInfo = new HashMap<String, String>();

			TargetGroup tg = targetGroupMapper.select(id);
			List<Customer> customers = surveyService.customerList(tg.getCategory1(), tg.getCategory2(), tg.getBranches(), tg.getLimit(), startDate, endDate);
			
			targetInfo.put("targetGroupName", tg.getTitle());
			targetInfo.put("count", String.valueOf(customers.size()));
			
			targetInfosByTargetGroup.add(targetInfo);
		}
		
		model.addAttribute("targetInfosByTargetGroup", targetInfosByTargetGroup);
		model.addAttribute("pageTitle", "타겟 그룹별 대상수");
		
		return "target_group_detail";
	}
}
