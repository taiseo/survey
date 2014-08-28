package com.freeneo.survey;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
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
	public String login(Model model){
		model.addAttribute("pageTitle", "로그인");
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
		String viewPage;
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user = userService.getUser(user);
		
		if(user != null){
			session.setAttribute("user", user);
			viewPage = "redirect:list";
		}else{
			model.addAttribute("login_failed_msg", "아이디나 비밀번호가 잘못됐습니다.");
			viewPage = "login";
		}
		
		return viewPage;
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("pageTitle", "설문 목록");
		return "list";
	}
}
