package com.freeneo.survey;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.mapper.QuestionMapper;

@RequestMapping(value="/questions")
@Controller
public class QuestionController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	QuestionMapper questionMapper;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Question insert(HttpServletRequest request, Question question){
		questionMapper.insert(question);
		logger.debug("insertedQuestion = {}", question);
		request.setAttribute("log_msg", "");
		return question;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Question update(HttpServletRequest request, Question question){
		logger.debug("question = {}", question);
		questionMapper.update(question);
		request.setAttribute("log_msg", "");
		return question;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Question delete(HttpServletRequest request, @RequestParam(value="id") Long id){
		Question question = questionMapper.select(id);
		logger.debug("question to delete = {}", question);
		questionMapper.delete(id);
		request.setAttribute("log_msg", "");
		
		// TODO responseItem 삭제
		
		return question;
	}
	
}
