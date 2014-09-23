package com.freeneo.survey.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.freeneo.survey.domain.ConfigItem;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.ConfigMapper;
import com.freeneo.survey.mapper.SVLogMapper;
import com.freeneo.survey.util.Util;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory
			.getLogger(CheckLoginInterceptor.class);

	@Autowired
	SVLogMapper svlogMapper;
	
	@Autowired
	ConfigMapper configMapper;	

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();

		ConfigItem domain = configMapper.select("domain");
		if (Util.getUri(request).contains(domain.getValue()) && ! request.getRequestURI().contains(
				request.getContextPath() + "/survey/")) {
			logger.debug("클라이언트가 /survey/외 접속 시도...-->차단요망");
			return false;
		}

		if (request.getRequestURI().contains(
				request.getContextPath() + "/survey/")) {
			logger.debug("클라이언트 접속");
			return true;
		}

		User user = (User) session.getAttribute("user");
		
		if (user == null) {

			// 로컬에서 개발중이면 로그인 검사 패스
			logger.debug("request.getRemoteAddr() = {}", request.getRemoteAddr());
			if (request.getRemoteAddr().equals("127.0.0.1")
					|| request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") 
					|| request.getRemoteAddr().equals("0:0:0:0:0:0:0:1%0")) {
				user = new User(4L, "admin", "", "로컬임의아이디", "임의 부서", "", "",
						"email", "시스템 관리자");
				session.setAttribute("user", user);
				logger.debug("로컬 개발용 임시 유저 = {}", user);
				return true;
			}

			logger.debug("로그인 필요");
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}else{
			logger.debug("로그인 확인됨 | user = {}", user);
		}

		return true;
	}
}
