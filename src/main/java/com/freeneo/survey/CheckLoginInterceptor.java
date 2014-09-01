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
		
		if((request.getContextPath() + "/login").equals(request.getRequestURI())) {
			return true;
		};
		
		if((request.getContextPath() + "/").equals(request.getRequestURI())) {
			return true;
		};
		
		if(request.getRequestURI().contains( request.getContextPath() + "/survey/")){
			logger.debug("클라이언트 접속");
			return true;
		}
		
		if(session.getAttribute("user") == null){
			
			// 로컬에서 개발중이면 로그인 검사 패스
			if(request.getRemoteAddr().equals("127.0.0.1") || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")){
				User user = new User(4L, "mytory", "", "로컬임의아이디", "임의 부서", "", "email", "admin");
				session.setAttribute("user", user);
				logger.debug("로컬 개발용 임시 유저 = {}", user);
				return true;
			}
			
			logger.debug("로그인 필요");
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		
		return true;
	}
}
