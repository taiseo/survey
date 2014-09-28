package com.freeneo.survey;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.ResponseMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.util.Util;

@Controller
@RequestMapping(value="/responses")
public class ResponseController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);
	
	@Autowired
	ResponseMapper responseMapper;
	@Autowired
	SurveyMapper surveyMapper;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response insert(Model model, HttpServletRequest request, Response response, HttpSession session) throws ParseException{
		logger.debug("response = {}", response);
		
		Survey survey = surveyMapper.select(response.getSurveyId());
		
		if( ! canResponse(survey, session)){
			return null;
		}
		
		if( ! response.getResponse().equals("")){
			Response selectedResponse = responseMapper.selectByQuestionIdAndRespondent(response);
			if(selectedResponse != null){
				response = update(selectedResponse);
			}else{
				responseMapper.insert(response);
			}
		}
		
		model.addAttribute("log_msg", response.toString());
		logger.debug("model ={}", model);
		
		request.setAttribute("log_msg", "");
		
		return response;
	}
	
	@RequestMapping(value="/multiple", method=RequestMethod.POST)
	@ResponseBody
	public List<Response> insert(
			@RequestParam(value="surveyId") Long surveyId,
			@RequestParam(value="questionId") Long questionId,
			@RequestParam(value="response[]") String[] response,
			@RequestParam(value="respondent") String respondent,
			HttpServletRequest request,
			HttpSession session
			) throws ParseException{
		
		Survey survey = surveyMapper.select(surveyId);
		
		if( ! canResponse(survey, session)){
			return null;
		}
		
		logger.debug("response = {}", response);
		
		Response tempResponse = new Response();
		tempResponse.setQuestionId(questionId);
		tempResponse.setRespondent(respondent);
		responseMapper.deleteByQuestionIdAndRespondent(tempResponse);
		
		List<Response> responses = new ArrayList<Response>();
		for(String res : response){
			Response responseToInsert = new Response();
			responseToInsert.setSurveyId(surveyId);
			responseToInsert.setQuestionId(questionId);
			responseToInsert.setResponse(res);
			responseToInsert.setRespondent(respondent);
			responseMapper.insert(responseToInsert);
			responses.add(responseToInsert);
		}
		
		request.setAttribute("log_msg", "");
		
		return responses;
	}
	
	private boolean canResponse(Survey survey, HttpSession session) throws ParseException {
		
		User user = (User) session.getAttribute("user");
		
		if(user != null){
			logger.debug("로그인한 사용자는 설문에 응답할 수 없습니다.");
			return false;
		}
		
		if( Util.compareWithToday(survey.getStartDate()) < 0 ){
			logger.debug("시작하지 않은 설문");
			return false;
		};
		if( Util.compareWithToday(survey.getEndDate()) > 0 ){
			logger.debug("종료한 설문");
			return false;
		};
		return true;
	}

	@RequestMapping(method=RequestMethod.PUT)
	public Response update(Response response){
		
		responseMapper.update(response);
		logger.debug("response = {}", response);
		
		return response;
	}
	
	@RequestMapping(value="/respondent", method=RequestMethod.POST)
	@ResponseBody
	public String insertRespondent(
			@RequestParam(value="bonbu") String bonbu,
			@RequestParam(value="branch") String branch,
			@RequestParam(value="surveyId") Long surveyId,
			HttpServletRequest request,
			HttpSession session
			){
		
		int respondentCount = responseMapper.countRespondentById(session.getId());
		
		logger.debug("respondentCount = {}", respondentCount);
		
		if(respondentCount > 0){
			responseMapper.updateRespondent(session.getId(), bonbu, branch, surveyId);
		}else{
			responseMapper.insertRespondent(session.getId(), bonbu, branch, surveyId);
		}
		
		request.setAttribute("log_msg", "");
		
		return "1";
	}
	
}
