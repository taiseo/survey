package com.freeneo.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public Question insert(Question question){
		
		logger.debug("question = {}", question);
		
		questionMapper.insert(question);
		
		logger.debug("inserted question = {}", question);

		return question;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Question update(Question question){
		
		logger.debug("question = {}", question);
		
		questionMapper.update(question);
		return question;
	}
	
}
