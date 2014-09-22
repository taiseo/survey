package com.freeneo.survey;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		
		List<TargetGroup> targetGroups = targetGroupMapper.list();
		
		logger.debug("targetGroups = {}", targetGroups);
		
		model.addAttribute("pageTitle", "캠페인(타겟) 그룹 목록");
		model.addAttribute("targetGroups", targetGroups);
		
		return "target_group_list";
	}
	
	@RequestMapping(value="/branches", method=RequestMethod.POST)
	@ResponseBody
	public List<String> selectByGroupIds(
			@RequestParam(value="targetGroupIds") String targetGroupIds
			) throws JsonParseException, JsonMappingException, IOException{
		List<String> branches = new ArrayList<String>();
		
		List<Long> targetGroupIdList = surveyService.makeSelectedTargetGroups(targetGroupIds);
		
		for(Long targetGroupId : targetGroupIdList){
			TargetGroup targetGroup = targetGroupMapper.select(targetGroupId);
			List<String> branchList = surveyService.makeBranchList(targetGroup.getBranches());
			branches.addAll(branchList);
		}
		
		// 중복 제거
		Set<String> setItems = new LinkedHashSet<String>(branches);
		branches.clear();
		branches.addAll(setItems);
		
		return branches;
	}
	
	@RequestMapping(value="/insert", method = RequestMethod.GET)
	public String insertPage(Model model){
		
		TargetGroup targetGroup = new TargetGroup();
		
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		// TODO targetGroup 객체에 세팅하는 것으로 변경
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
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

		// TODO 저장한 데서 불러오게 되면 삭제.
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		
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
		

}
