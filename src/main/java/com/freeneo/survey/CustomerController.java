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
import com.freeneo.survey.mapperCrm.CustomerMapper;

@RequestMapping(value="/customers")
@Controller
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	CustomerMapper customerMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String users(Model model, HttpSession session) {
		
		List<Customer> customers = customerMapper.list();
		
		logger.debug("customers = {}", customers);
		
		model.addAttribute("pageTitle", "캠페인(타겟)");
		model.addAttribute("customers", customers);

		return "customer_list";
	}

	@RequestMapping(value="/branch-list", method = RequestMethod.POST)
	public String branchList(
			@RequestParam(value="category") String category,
			Model model){
		
		logger.debug("category = {}", category);
		
		List<String> branches = customerMapper.branchList(category);
		logger.debug("branches = {}", branches);
		
		model.addAttribute("branches", branches);
		
		return "include/branch_list";
	}
}
