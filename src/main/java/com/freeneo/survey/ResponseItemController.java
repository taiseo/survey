package com.freeneo.survey;

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

import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.mapper.ResponseItemMapper;

@RequestMapping(value="/response_item")
@Controller
public class ResponseItemController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseItemController.class);
	
	@Autowired
	ResponseItemMapper responseItemMapper;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseItem insert(ResponseItem responseItem){
		responseItemMapper.insert(responseItem);
		logger.debug("insertedResponseItem = {}", responseItem);
		return responseItem;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseItem update(ResponseItem responseItem){
		logger.debug("responseItem = {}", responseItem);
		responseItemMapper.update(responseItem);
		return responseItem;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseItem delete(@RequestParam(value="id") Long id){
		ResponseItem responseItem = responseItemMapper.select(id);
		logger.debug("responseItem to delete = {}", responseItem);
		responseItemMapper.delete(id);
		
		// TODO responseItem 삭제
		
		return responseItem;
	}
	
}
