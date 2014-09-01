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
import com.freeneo.survey.util.Util;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		
		String viewPage = "redirect:login";
		if(session.getAttribute("user") != null){
			viewPage = "redirect:/surveys";
		}
		
		return viewPage;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpSession session){
		model.addAttribute("pageTitle", "로그인");
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session){
		session.setAttribute("user", null);
		return login(model, session);
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
		user.setPasswordToHash();
		user = userService.getUser(user);
		
		if(user != null){
			logger.debug("로그인한 사용자 = {} ", user);
			session.setAttribute("user", user);
			return "redirect:/surveys";
		}
		
		logger.debug("로그인 실패");
		model.addAttribute("error_msg", "아이디나 비밀번호가 잘못됐습니다.");
		return login(model, session);
	}
}