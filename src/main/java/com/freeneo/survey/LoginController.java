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

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		
		String viewPage = "redirect:login";
		if(session.getAttribute("user") != null){
			viewPage = "redirect:list";
		}
		
		return viewPage;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginAction(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password,
			HttpSession session,
			Model model
			) {
//		model.addAttribute("serverTime", formattedDate );
		
		User user = null;
		String viewPage = "login";
		
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
