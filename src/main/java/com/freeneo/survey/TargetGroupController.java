package com.freeneo.survey;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.TargetGroup;
import com.freeneo.survey.mapper.TargetGroupMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;

@RequestMapping(value="/target-groups")
@Controller
public class TargetGroupController {
	private static final Logger logger = LoggerFactory.getLogger(TargetGroupController.class);
	
	@Autowired CustomerMapper customerMapper;
	@Autowired TargetGroupMapper targetGroupMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		
		List<TargetGroup> targetGroups = targetGroupMapper.list();
		
		logger.debug("targetGroups = {}", targetGroups);
		
		model.addAttribute("pageTitle", "캠페인(타겟) 그룹 목록");
		model.addAttribute("targetGroups", targetGroups);
		
		return "target_group_list";
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
		

}
