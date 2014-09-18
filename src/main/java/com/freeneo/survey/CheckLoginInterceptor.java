package com.freeneo.survey;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.freeneo.survey.domain.SVLog;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.SVLogMapper;
import com.freeneo.survey.util.Util;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory
			.getLogger(CheckLoginInterceptor.class);

	@Autowired
	SVLogMapper svlogMapper;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();

		if ((request.getContextPath() + "/login").equals(request
				.getRequestURI())) {
			return true;
		}
		;

		if ((request.getContextPath() + "/").equals(request.getRequestURI())) {
			return true;
		}
		;

		if (request.getRequestURI().contains(
				request.getContextPath() + "/survey/")) {
			logger.debug("클라이언트 접속");
			return true;
		}

		if (session.getAttribute("user") == null) {

			// 로컬에서 개발중이면 로그인 검사 패스
			if (request.getRemoteAddr().equals("127.0.0.1")
					|| request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
				User user = new User(4L, "mytory", "", "로컬임의아이디", "임의 부서", "",
						"email", "시스템 관리자");
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

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			
			Map<String, Object> model = modelAndView.getModel();
			logger.debug("log_msg = {}", model.containsKey("log_msg"));

			String id = session.getId();
			SVLog log = svlogMapper.selectById(id);
			boolean isNew = false;
			
			if(log == null){
				log = new SVLog();
				log.setId(id);
				isNew = true;
			}

			String ip = request.getRemoteAddr();

			log.setId(session.getId());
			log.setIp(ip);
			log.setUsername(user.getUsername());

			String newContent;
			
			if (model.containsKey("log_msg")) {
				newContent = model.get("log_msg").toString();
			} else if (model.containsKey("pageTitle")) {
				newContent = model.get("pageTitle").toString();
			} else {
				newContent = Util.getUri(request);
			}

			if(isNew){
				log.setContent(newContent);
				svlogMapper.insert(log);
			}else{
				log.setContent(log.getContent() + "\n" + newContent);
				svlogMapper.update(log);
			}
		}
	}

}
