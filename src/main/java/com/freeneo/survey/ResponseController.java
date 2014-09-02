package com.freeneo.survey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freeneo.survey.domain.Response;
import com.freeneo.survey.mapper.ResponseMapper;

@Controller
@RequestMapping(value="/responses")
public class ResponseController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);
	
	@Autowired
	ResponseMapper responseMapper;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response insert(Response response){
		
		logger.debug("response = {}", response);
		
		List<Response> responses = responseMapper.selectByQuestionIdAndRespondent(response);
		if(responses.size() > 0){
			response = update(response);
		}else{
			responseMapper.insert(response);
		}
		
		return response;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public Response update(Response response){
		
		logger.debug("response = {}", response);
		
		return response;
	}
	
}
