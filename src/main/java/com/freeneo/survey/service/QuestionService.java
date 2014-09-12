package com.freeneo.survey.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.mapper.ResponseMapper;

@Service
public class QuestionService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
	
	@Autowired ResponseMapper responseMapper;
	
	public void setPointResponseCount(Question question){
		if( ! question.getType().equals("점수범위")){
			return;
		}
		if(question.getResponseItems() == null){
			logger.debug("responseItems가 세팅돼 있어야 점수범위 답변을 수집할 수 있습니다.");
			return;
		}
		int min = question.getResponseItems().get(0).getMin();
		int max = question.getResponseItems().get(0).getMax();
		
		TreeMap<Integer, Long> pointResponseCount = new TreeMap<Integer, Long>();
		for(int i = min; i < max; i++){
			pointResponseCount.put(i, question.getResponseCount(i));
		}
		
		question.setPointResponseCount(pointResponseCount);
	}
	
	public void setEtcResponses(Question question){
		if( ! question.getType().contains("객관식")){
			return;
		}
		if(question.getResponseItems() == null){
			logger.debug("responseItems가 세팅돼 있어야 기타 항목을 수집할 수 있습니다.");
			return;
		}
		
		logger.debug("responseMapper = {}", responseMapper);
		
		List<Response> responses = responseMapper.selectByQuestionId(question.getId());
		List<String> etcResponses = new ArrayList<String>();
		for(Response response : responses){
			boolean etcItem = true;
			for(ResponseItem responseItem : question.getResponseItems()){
				if(response.getResponse().equals(responseItem.getContent())){
					etcItem = false;
				}
			}
			if(etcItem){
				etcResponses.add(response.getResponse());
			}
		}
		
		question.setEtcResponses(etcResponses);
	}
}
