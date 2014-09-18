package com.freeneo.survey.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.User;
import com.freeneo.survey.domain.UserHistory;
import com.freeneo.survey.mapper.SurveyMapper;

@Service
public class HistoryService {

	private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);
	
	@Autowired SurveyMapper surveyMapper;
	@Autowired SurveyService surveyService;
	
	public UserHistory getUserHistory(User user) {
		
		logger.debug("user = {}", user);
		
		List<Survey> surveys = surveyMapper.selectByWriter(user.getUsername());
		
		logger.debug("surveys = {}", surveys);
		
		int surveyCount = 0;
		int sendCount = 0;
		int respondentCount = 0;
		
		for(Survey survey : surveys){
			surveyCount++;
			sendCount += survey.getSendCount();
			respondentCount += surveyMapper.selectRespondentCount(survey.getId());
		}
		
		UserHistory userHistory = new UserHistory();
		userHistory.setSurveyCount(surveyCount);
		userHistory.setSendCount(sendCount);
		userHistory.setRespondentCount(respondentCount);
		userHistory.setResponseRatio(sendCount == 0 ? 0 : (double)respondentCount/(double)sendCount*100.0);
		userHistory.setUser(user);
		
		return userHistory;
	}

	public UserHistory getUserHistory(User user, String startDate,
			String endDate) {
		List<Survey> surveys = surveyMapper.selectByWriterAndDate(user.getUsername(), startDate, endDate);
		
		logger.debug("surveys = {}", surveys);
		
		int surveyCount = 0;
		int sendCount = 0;
		int respondentCount = 0;
		
		for(Survey survey : surveys){
			surveyCount++;
			sendCount += survey.getSendCount();
			respondentCount += surveyMapper.selectRespondentCount(survey.getId());
		}
		
		UserHistory userHistory = new UserHistory();
		userHistory.setSurveyCount(surveyCount);
		userHistory.setSendCount(sendCount);
		userHistory.setRespondentCount(respondentCount);
		userHistory.setResponseRatio(sendCount == 0 ? 0 : (double)respondentCount/(double)sendCount*100.0);
		userHistory.setUser(user);
		
		return userHistory;
	}
	
}
