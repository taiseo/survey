package com.freeneo.survey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(CheckLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		
		logger.debug(request.getRequestURI());
		
		if((request.getContextPath() + "/login").equals(request.getRequestURI())) {
			return true;
		};
		
		if((request.getContextPath() + "/").equals(request.getRequestURI())) {
			return true;
		};
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		
		return true;
	}
}
