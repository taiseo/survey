package com.freeneo.survey.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeneo.survey.domain.Target;
import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Mms;
import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.mapperMms.MmsMapper;

@Service
public class SurveyService {

	static Logger logger = LoggerFactory.getLogger(SurveyService.class);

	@Autowired
	SurveyMapper surveyMapper;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	ResponseItemMapper responseItemMapper;
	@Autowired
	TargetMapper targetMapper;
	@Autowired
	MmsMapper mmsMapper;

	public Survey getFullSurvey(Long id) {
		Survey survey = surveyMapper.select(id);
		List<Question> questions = questionMapper.list(survey.getId());
		for (Question question : questions) {
			List<ResponseItem> responseItems = responseItemMapper.list(question
					.getId());
			if (question.getType().equals("점수범위")) {
				responseItems.get(0).setMinMax();
			}
			question.setResponseItems(responseItems);
		}
		survey.setQuestions(questions);

		return survey;
	}

	public List<Customer> customerList(String targetCategory1,
			String targetCategory2, String targetBranches, int limit)
			throws JsonParseException, JsonMappingException, IOException {
		String category = null;
		if (targetCategory2 == null) {
			category = targetCategory1;
		} else {
			category = targetCategory2;
		}

		List<String> branchList = makeBranchList(targetBranches);

		List<Customer> customers = new ArrayList<Customer>();
		for(String branch : branchList){
			customers.addAll(customerMapper.customerList(category, branch, limit));
			logger.debug("customers = {}", customers);
		}
		
		
		return customers;
	}

	public List<String> makeBranchList(String targetBranches)
			throws JsonParseException, JsonMappingException, IOException {

		logger.debug("targetBranches = {}", targetBranches);

		if (targetBranches == null) {
			return null;
		}

		ObjectMapper objectMapper = new ObjectMapper();

		List<String> branches = objectMapper.readValue(
				targetBranches,
				objectMapper.getTypeFactory().constructCollectionType(
						List.class, String.class));

		logger.debug("branches = {}", branches);

		return branches;
	}

	public void insertTargets(Long surveyId, List<Customer> customers) {
		targetMapper.insertAll(surveyId, customers);
	}

	public void updateTargets(Long surveyId, List<Customer> customers) {
		targetMapper.delete(surveyId);
		targetMapper.insertAll(surveyId, customers);
	}

	public void sendMms(Survey survey) throws JsonParseException, JsonMappingException, IOException {
		
		List<Customer> customers = customerList(survey.getTargetCategory1(), survey.getTargetCategory2(), survey.getTargetBranches(), survey.getLimit());
		
		logger.debug("customers = {}", customers);
		
		List<Mms> mmsList = new ArrayList<Mms>();
		
		for(Customer customer : customers){
			Mms mms = new Mms();
			mms.setSubject(survey.getMsgSubject());
			mms.setPhone(customer.getHp());
			mms.setCallback("0000");
			mms.setMsg(survey.getMsg() + " http://192.168.0.5:8080/survey/survey/" + survey.getId());
			mmsList.add(mms);
		}
		
		logger.debug("mmsList = {}", mmsList);
		
		for(Mms mms : mmsList){
			mmsMapper.insert(mms);
		}
		
		updateTargets(survey.getId(), customers);
	}

}
