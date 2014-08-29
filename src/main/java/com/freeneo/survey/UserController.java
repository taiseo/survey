package com.freeneo.survey;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.service.UserService;

@RequestMapping(value="/users")
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String users(Model model) {
		
		List<User> users = userService.getUsers();
		model.addAttribute("pageTitle", "사용자 목록");
		model.addAttribute("users", users);

		return "user_list";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String update(
			@PathVariable(value="id") Long id,
			HttpServletRequest request,
			Model model
			) {

		User user = new User();
		user.setId(id);
		User selectedUser = userService.getUserById(user);

		logger.debug(selectedUser.toString());
		
		model.addAttribute("pageCommand", "update");
		model.addAttribute("pageTitle", selectedUser.getName() + " 님 정보 변경");
		model.addAttribute("user", selectedUser);
		model.addAttribute("httpMethod", "PUT");
		
		return "user_update";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String updateAction(
			@PathVariable(value="id") Long id,
			@RequestParam(value="username") String username,
			@RequestParam(value="password") String password,
			@RequestParam(value="passwordConfirm") String passwordConfirm,
			@RequestParam(value="oldPassword") String oldPassword,
			@RequestParam(value="name") String name,
			@RequestParam(value="part") String part,
			@RequestParam(value="tel") String tel,
			@RequestParam(value="email") String email,
			@RequestParam(value="userLevel") String userLevel,
			HttpServletRequest request,
			HttpSession session,
			Model model
			){
		
		User newUser = new User(id, username, password, name, part, tel, email, userLevel);
		newUser.setPasswordToHash();
		User oldUser = userService.getUserById(newUser);
		User currentUser = (User) session.getAttribute("user");
		
		if( ! password.equals("") || ! passwordConfirm.equals("")){
			// 암호 변경 시도
			if( ! currentUser.getUserLevel().equals("admin") && ! oldUser.getPassword().equals(newUser.getPassword())){
				// 관리자가 아닌 경우엔 기존 비밀번호 검사
				model.addAttribute("error_msg", "기존 비밀번호가 틀렸습니다.");
				model.addAttribute("user", newUser);
				return update(id, request, model);
			}
			
			if( ! password.equals(passwordConfirm)){
				// 새로 입력한 비밀번호 확인
				model.addAttribute("error_msg", "새로 입력한 비밀번호가 서로 맞지 않습니다.");
				model.addAttribute("user", newUser);
				return update(id, request, model);
			}
		}else if(password.equals("") && oldPassword.equals("") && oldPassword.equals("")){
			// 암호 변경이 없는 경우 옛 패스워드 그대로
			newUser.setPassword(oldUser.getPassword());
		}
		
		if(password.equals("")){
			logger.debug("암호랑 따옴표 빈값 비교 가능");
		}
		
		logger.debug("oldUser: " + oldUser.toString());
		logger.debug("newUser: " + newUser.toString());
		
		userService.updateUser(newUser);
		session.setAttribute("user", newUser);
		return "redirect:/users/";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(
			HttpServletRequest request,
			Model model
			) {

		User user = new User();
		user.setUserLevel("normal");
		model.addAttribute("pageCommand", "insert");
		model.addAttribute("pageTitle", "사용자 입력");
		model.addAttribute("user", user);
		model.addAttribute("httpMethod", "POST");
		
		return "user_update";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertAction(
			@RequestParam(value="username") String username,
			@RequestParam(value="password") String password,
			@RequestParam(value="passwordConfirm") String passwordConfirm,
			@RequestParam(value="name") String name,
			@RequestParam(value="part") String part,
			@RequestParam(value="tel") String tel,
			@RequestParam(value="email") String email,
			@RequestParam(value="userLevel") String userLevel,
			HttpServletRequest request,
			Model model
			) {
		
		User newUser = new User(null, username, password, name, part, tel, email, userLevel);
		boolean validationError = false;
		
		// 필수 입력 항목 검사
		if(username.equals("") || name.equals("") || password.equals("") || passwordConfirm.equals("")){
			model.addAttribute("error_msg", "필수 항목(아이디, 이름, 비밀번호, 비밀번호 확인) 중 일부를 입력하지 않았습니다.");
			validationError = true;
		}
		
		// 아이디 중복 검사
		User usernameDuplicatedUser = userService.getUserByUsername(newUser);
		if(usernameDuplicatedUser != null){
			model.addAttribute("error_msg", username + "은 이미 있는 아이디입니다. 다른 아이디를 사용하세요.");
			validationError = true;
		}
		
		if(validationError){
			model.addAttribute("user", newUser);
			model.addAttribute("pageTitle", "사용자 입력");
			model.addAttribute("pageCommand", "insert");
			model.addAttribute("httpMethod", "POST");
			return "user_update";
		}
		
		newUser.setPasswordToHash();
		userService.insertUser(newUser);
		
		return "redirect:/users";
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String deleteAction(
			@PathVariable(value="id") Long id,
			HttpServletRequest request,
			Model model
			){
		User user = new User();
		user.setId(id);
		user = userService.getUserById(user);
		if(user.getUsername().equals("admin")){
			model.addAttribute("error_msg", "admin은 삭제할 수 없습니다.");
			return users(model);
		}
		userService.deleteUser(user);
		return "redirect:/users";
	}
}
