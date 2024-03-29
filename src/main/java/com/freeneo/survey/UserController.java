package com.freeneo.survey;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
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

import com.freeneo.survey.domain.User;
import com.freeneo.survey.service.UserService;
import com.freeneo.survey.util.Util;

@RequestMapping(value="/users")
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String users(Model model, HttpSession session) {
		return users("1", model, session);
	}
	
	@RequestMapping(value = "/page/{page}", method = RequestMethod.GET)	
	public String users(
			@PathVariable(value = "page") String page,
			Model model, 
			HttpSession session) {
		
		User currentUser = (User) session.getAttribute("user");
		if( ! currentUser.getUserLevel().equals("시스템 관리자")){
			return "redirect:/";
		}
		
		List<User> users = userService.getUsers();
		
		for (User user : users){
			String userLevel = user.getUserLevel();
			if(userLevel.equals("admin")){
				user.setUserLevel("시스템 관리자");
			}
			if(userLevel.equals("승인자2")){
				user.setUserLevel("결제권한 : 승인 (문자발송 최종 승인)");
			}
			if(userLevel.equals("승인자1")){
				user.setUserLevel("결제권한 : 심의 (문자발송 승인)");
			}
			if(userLevel.equals("일반")){
				user.setUserLevel("사용자");
			}
						
		}
		
		PagedListHolder<User> pagedListHolder = new PagedListHolder<User>(
				users);
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
		
		model.addAttribute("pageTitle", "사용자 목록");
		model.addAttribute("pagedListHolder", pagedListHolder);

		return "user_list";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String update(
			@PathVariable(value="id") Long id,
			HttpServletRequest request,
			HttpSession session,
			Model model
			) {

		User currentUser = (User) session.getAttribute("user");
		
		// 최고관리자가 아닌데 남의 정보를 변경하려 들면 첫 화면으로 보내 버린다.
		if( ! currentUser.getUserLevel().equals("시스템 관리자") && currentUser.getId() != id){
			return "redirect:/";
		}
		
		User user = new User();
		user.setId(id);
		User selectedUser = userService.getUserById(user);

		model.addAttribute("pageCommand", "update");
		model.addAttribute("pageTitle", selectedUser.getName() + " 님 정보 변경");
		model.addAttribute("user", selectedUser);
		model.addAttribute("httpMethod", "PUT");
		
		String listUrl = request.getContextPath() + "/users";
		model.addAttribute("listUrl", Util.getListUrl(request, listUrl));
		
		return "user_manage";
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
			@RequestParam(value="hp") String hp,
			@RequestParam(value="email") String email,
			@RequestParam(value="userLevel") String userLevel,
			HttpServletRequest request,
			HttpSession session,
			Model model
			){
		
		User newUser = new User(id, username, password, name, part, tel, hp, email, userLevel);
		newUser.setPasswordToHash();
		User oldUser = userService.getUserById(newUser);
		User currentUser = (User) session.getAttribute("user");
		
		// 시스템 관리자가 아니면 등급 변경 불가
		if( ! currentUser.getUserLevel().equals("시스템 관리자")){
			newUser.setUserLevel(oldUser.getUserLevel());
		}
		
		logger.debug("변경할 정보 : " + newUser);
		logger.debug("예전 정보 : " + oldUser);
		
		// 남의 정보는 최고관리자만 변경 가능
		if( ! currentUser.getUserLevel().equals("시스템 관리자") && oldUser.getId() != currentUser.getId()){
			return "redirect:/";
		}
		
		// 암호 변경 시도가 있는 경우
		if( ! password.equals("") || ! passwordConfirm.equals("")){
			String oldPasswordHashed = DigestUtils.sha1Hex(oldPassword + "!@#$asdf");
			if( ! currentUser.getUserLevel().equals("시스템 관리자") && ! oldUser.getPassword().equals(oldPasswordHashed)){
				// 관리자가 아닌 경우엔 기존 비밀번호 검사
				model.addAttribute("error_msg", "기존 비밀번호가 틀렸습니다.");
				model.addAttribute("user", newUser);
				return update(id, request, session, model);
			}
			
			if( ! password.equals(passwordConfirm)){
				// 새로 입력한 비밀번호 확인
				model.addAttribute("error_msg", "새로 입력한 비밀번호가 서로 맞지 않습니다.");
				model.addAttribute("user", newUser);
				return update(id, request, session, model);
			}
		}else{
			// 암호 변경이 없는 경우 옛 패스워드 그대로
			newUser.setPassword(oldUser.getPassword());
		}
		
		logger.debug("oldUser: " + oldUser.toString());
		logger.debug("newUser: " + newUser.toString());
		
		userService.updateUser(newUser);
		if(currentUser.getId() == id){
			session.setAttribute("user", newUser);
		}
		
		if(currentUser.getUserLevel().equals("시스템 관리자")){
			return "redirect:/users/";
		}else{
			model.addAttribute("success_msg", "정보를 수정했습니다.");
			return update(id, request, session, model);
		}
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(
			HttpServletRequest request,
			Model model
			) {

		User user = new User();
		user.setUserLevel("일반");
		model.addAttribute("pageCommand", "insert");
		model.addAttribute("pageTitle", "사용자 입력");
		model.addAttribute("user", user);
		model.addAttribute("httpMethod", "POST");
		
		String listUrl = request.getContextPath() + "/users";
		model.addAttribute("listUrl", Util.getListUrl(request, listUrl));
		
		return "user_manage";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertAction(
			@RequestParam(value="username") String username,
			@RequestParam(value="password") String password,
			@RequestParam(value="passwordConfirm") String passwordConfirm,
			@RequestParam(value="name") String name,
			@RequestParam(value="part") String part,
			@RequestParam(value="tel") String tel,
			@RequestParam(value="hp") String hp,
			@RequestParam(value="email") String email,
			@RequestParam(value="userLevel") String userLevel,
			HttpServletRequest request,
			Model model
			) {
		
		User newUser = new User(null, username, password, name, part, tel, hp, email, userLevel);
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
			return "user_manage";
		}
		
		newUser.setPasswordToHash();
		userService.insertUser(newUser);
		
		return "redirect:/users";
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String deleteAction(
			@PathVariable(value="id") Long id,
			HttpServletRequest request,
			HttpSession session,
			Model model
			){
		User user = new User();
		user.setId(id);
		user = userService.getUserById(user);
		if(user.getUsername().equals("시스템 관리자")){
			model.addAttribute("error_msg", "시스템 관리자는 삭제할 수 없습니다.");
			return users(model, session);
		}
		userService.deleteUser(user);
		return "redirect:/users";
	}
}
