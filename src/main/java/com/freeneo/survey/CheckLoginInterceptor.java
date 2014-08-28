package com.freeneo.survey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.freeneo.survey.domain.User;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(CheckLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {

		HttpSession session = request.getSession();
		
		// 로컬에서 개발중이면 로그인 검사 패스
		if(request.getRemoteAddr().equals("127.0.0.1") || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")){
			session.setAttribute("user", new User(1L, "admin", "", "최고관리자", "최고관리자", "", "email", "admin"));
			return true;
		}
		
		if((request.getContextPath() + "/login").equals(request.getRequestURI())) {
			return true;
		};
		
		if((request.getContextPath() + "/").equals(request.getRequestURI())) {
			return true;
		};
		
		if(session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		
		return true;
	}
}
