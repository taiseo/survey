package com.freeneo.survey.interceptor;

import java.util.HashMap;
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
import com.freeneo.survey.mapper.ConfigMapper;
import com.freeneo.survey.mapper.SVLogMapper;
import com.freeneo.survey.util.Util;

public class LogInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory
			.getLogger(LogInterceptor.class);

	@Autowired
	SVLogMapper svlogMapper;
	
	@Autowired
	ConfigMapper configMapper;	

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			
			String id = session.getId();
			SVLog log = svlogMapper.selectByIdAndUsername(id, user.getUsername());
			
			logger.debug("id = {}", id);
			logger.debug("user.username = {}", user.getUsername());
			logger.debug("log = {}", log);
			
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

			Map<String, Object> model = new HashMap<String, Object>();
			if(modelAndView != null){
				model = modelAndView.getModel();
			}
			logger.debug("model = {}", model);
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
				log.setContent(log.getContent() + "<br>" + newContent);
				svlogMapper.update(log);
			}
		}
	}

}
