package com.freeneo.survey;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.service.UserService;

@RequestMapping(value="/customers")
@Controller
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	CustomerMapper customerMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String users(Model model, HttpSession session) {
		
		Customer c = new Customer();
		Random r = new Random();
		
		c.setCstNo(String.valueOf(r.nextInt()));
		c.setCstNm("안형우");
		c.setHp("01044533153");
		
		customerMapper.insert(c);
		
		List<Customer> customers = customerMapper.list();
		
		logger.debug("customers = {}", customers);
		
		model.addAttribute("pageTitle", "캠페인(타겟)");
		model.addAttribute("customers", customers);

		return "customer_list";
	}	
}
