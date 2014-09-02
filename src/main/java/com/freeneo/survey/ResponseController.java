package com.freeneo.survey;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="/multiple", method=RequestMethod.POST)
	@ResponseBody
	public List<Response> insert(
			@RequestParam(value="questionId") Long questionId,
			@RequestParam(value="response[]") String[] response,
			@RequestParam(value="respondent") String respondent
			){
		
		logger.debug("response = {}", response);
		
		responseMapper.deleteByQuestionId(questionId);
		
		List<Response> responses = new ArrayList<Response>();
		for(String res : response){
			Response responseToInsert = new Response();
			responseToInsert.setQuestionId(questionId);
			responseToInsert.setResponse(res);
			responseToInsert.setRespondent(respondent);
			responseMapper.insert(responseToInsert);
			responses.add(responseToInsert);
		}
		
		return responses;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public Response update(Response response){
		
		logger.debug("response = {}", response);
		
		return response;
	}
	
}
