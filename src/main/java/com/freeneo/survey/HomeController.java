package com.freeneo.survey;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password,
			HttpSession session,
			Model model
			) {
//		model.addAttribute("serverTime", formattedDate );
		
		User user = null;
		String viewPage = "home";
		
		if(username.equals("test") && password.equals("test")){
			user = new User();
			user.setUsername("test");
			user.setPassword("test");
			session.setAttribute("user", user);
			viewPage = "redirect:list";
		}
		
		return viewPage;
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list(){
		return "list";
	}
}
