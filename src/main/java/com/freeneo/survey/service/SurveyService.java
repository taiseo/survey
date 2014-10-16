package com.freeneo.survey.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Mms;
import com.freeneo.survey.domain.Question;
import com.freeneo.survey.domain.ResponseItem;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.Target;
import com.freeneo.survey.domain.TargetGroup;
import com.freeneo.survey.mapper.ConfigMapper;
import com.freeneo.survey.mapper.QuestionMapper;
import com.freeneo.survey.mapper.ResponseItemMapper;
import com.freeneo.survey.mapper.SurveyMapper;
import com.freeneo.survey.mapper.TargetGroupMapper;
import com.freeneo.survey.mapperInner.TargetMapper;
import com.freeneo.survey.mapperCrm.CustomerMapper;
import com.freeneo.survey.mapperMms.MmsMapper;
import com.freeneo.survey.util.Util;

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
	@Autowired
	TargetGroupMapper targetGroupMapper;
	@Autowired
	ConfigMapper configMapper;

	public Survey getFullSurvey(Long id) {
		Survey survey = surveyMapper.select(id);
		if(survey == null){
			return survey;
		}
		List<Question> questions = questionMapper.list(survey.getId());
		for (Question question : questions) {
			List<ResponseItem> responseItems = responseItemMapper.list(question
					.getId());
			if (question.getType().equals("점수범위") && responseItems.size() > 0) {
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
		
		return customerListCommon(targetCategory1, targetCategory2, targetBranches, limit, null, null);
	}
	
	public List<Customer> customerList(String targetCategory1,
			String targetCategory2, String targetBranches, int limit,
			String startDate, String endDate)
			throws JsonParseException, JsonMappingException, IOException {
		
		if(startDate.length() > 8){
			startDate = startDate.replace("-", "").substring(0, 8);
			endDate = endDate.replace("-", "").substring(0, 8);
		}
		
		logger.debug("startDate = {}", startDate);
		logger.debug("endDate = {}", endDate);
		
		return customerListCommon(targetCategory1, targetCategory2, targetBranches, limit, startDate, endDate);
	}
	
	private List<Customer> customerListCommon(String targetCategory1,
			String targetCategory2, String targetBranches, int limit,
			String startDate, String endDate) throws JsonParseException, JsonMappingException, IOException{
		
		String category = null;
		if (Util.isEmptyStr(targetCategory2)) {
			category = targetCategory1;
		} else {
			category = targetCategory2;
		}

		List<String> branchList = makeBranchList(targetBranches);

		List<Customer> customers = new ArrayList<Customer>();
		for (String branch : branchList) {
			List<Customer> temp = new ArrayList<Customer>();
			
			if(Util.isEmptyStr(startDate) || Util.isEmptyStr(endDate)){
				temp = customerMapper.customerList(category, branch, limit);
			}else{
				temp = customerMapper.customerListByDate(category, branch, limit, startDate + "000000", endDate + "235959");
			}
			
			customers.addAll(temp);
			
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

	public boolean sendMms(Survey survey, HttpServletRequest request, Model model) throws JsonParseException,
			JsonMappingException, IOException {

		List<Customer> customers = null;
		List<Target> targets = null;
		
		if(survey.getTargetRegistrationType().equals("CRM DB 추출")){
			customers = customerList(survey.getTargetCategory1(),
					survey.getTargetCategory2(), survey.getTargetBranches(),
					survey.getLimit(), survey.getTargetStartDate(), survey.getTargetEndDate());
		}else if(survey.getTargetRegistrationType().equals("캠페인 그룹 선택")){
			customers = customerListByTargetGroupIds(survey.getTargetGroupIds(), survey.getTargetStartDate(), survey.getTargetEndDate());
		}else if(survey.getTargetRegistrationType().equals("엑셀파일 업로드")){
			targets = targetMapper.selectBySurveyId(survey.getId()); 
		}

		logger.debug("customers = {}", customers);

		List<Mms> mmsList = new ArrayList<Mms>();
		
		String domain = configMapper.select("domain").getValue();

		if(survey.getTargetRegistrationType().equals("엑셀파일 업로드")){
			for (Target target : targets) {
				Mms mms = new Mms();
				mms.setSubject(survey.getMsgSubject());
				mms.setPhone(target.getHp());
				mms.setCallback(configMapper.select("phone").getValue());
				mms.setFilePath1(request.getRealPath("/images/upload_logo.jpg"));
				
	
				mms.setMsg(
						survey.getMsgSubject() 
						+ " / "
						+ survey.getMsg()
						+ " "+ domain + "/survey/survey/"
						+ survey.getId());
				
				mmsList.add(mms);
			}			
		}else{
		
			for (Customer customer : customers) {
				Mms mms = new Mms();
				mms.setSubject(survey.getMsgSubject());
				mms.setPhone(customer.getHp());
				mms.setCallback(configMapper.select("phone").getValue());
				mms.setFilePath1(request.getRealPath("/images/upload_logo.jpg"));
				
	
				mms.setMsg(
						survey.getMsgSubject() 
						+ " / "
						+ survey.getMsg()
						+ " "+ domain + "/survey/survey/"
						+ survey.getId());
				
				mmsList.add(mms);
			}
		}

		logger.debug("mmsList = {}", mmsList);

		if(mmsList.size() == 0){
			model.addAttribute("error_msg", "발송 대상이 0명입니다.");
			return false;
		}
		
		for (Mms mms : mmsList) {
			mmsMapper.insert(mms);
		}
		
		if(!survey.getTargetRegistrationType().equals("엑셀파일 업로드")){
			updateTargets(survey.getId(), customers);
			survey.setSendCount(customers.size());
		}else{
			survey.setSendCount(targets.size());
		}
		
		surveyMapper.update(survey);
		return true;
	}

	private List<Customer> customerListByTargetGroupIds(String targetGroupIds, String startDate, String endDate) 
			throws JsonParseException, JsonMappingException, IOException {
		
		List<Customer> customers = new ArrayList<Customer>(); 
		List<Long> targetGroupIdList = makeSelectedTargetGroups(targetGroupIds);
		for(Long id : targetGroupIdList){
			TargetGroup targetGroup = targetGroupMapper.select(id);
			customers.addAll(customerList(
					targetGroup.getCategory1(), 
					targetGroup.getCategory2(), 
					targetGroup.getBranches(), 
					targetGroup.getLimit(),
					startDate,
					endDate));
		}
		
		// 중복 제거
		Set<Customer> setItems = new LinkedHashSet<Customer>(customers);
		customers.clear();
		customers.addAll(setItems);
		
		return customers;
	}

	public List<Long> makeSelectedTargetGroups(String targetGroupIds)
			throws JsonParseException, JsonMappingException, IOException {
		logger.debug("targetGroupIds = {}", targetGroupIds);

		if (targetGroupIds == null) {
			return null;
		}

		ObjectMapper objectMapper = new ObjectMapper();

		List<Long> targetGroupIdList = objectMapper.readValue(
				targetGroupIds,
				objectMapper.getTypeFactory().constructCollectionType(
						List.class, Long.class));

		logger.debug("targetGroupIdList = {}", targetGroupIdList);

		return targetGroupIdList;
	}
	
	public List<String> getBranchesByTargetGroupIds(String targetGroupIds) throws JsonParseException, JsonMappingException, IOException {
		List<String> branches = new ArrayList<String>();
		
		List<Long> targetGroupIdList = makeSelectedTargetGroups(targetGroupIds);
		
		for(Long targetGroupId : targetGroupIdList){
			TargetGroup targetGroup = targetGroupMapper.select(targetGroupId);
			List<String> branchList = makeBranchList(targetGroup.getBranches());
			branches.addAll(branchList);
		}
		
		// 중복 제거
		Set<String> setItems = new LinkedHashSet<String>(branches);
		branches.clear();
		branches.addAll(setItems);
		
		return branches;
	}

	public int customerCount(String category1, String category2,
			String branches, int limit, String startDate, String endDate) throws JsonParseException, JsonMappingException, IOException {

		// 기간은 필수
		if(Util.isEmptyStr(startDate)){
			startDate = "1970-01-01";
		}
		if(Util.isEmptyStr(endDate)){
			endDate = "2999-12-31";
		}
		
		if( ! Util.isEmptyStr(startDate) && startDate.length() > 8){
			startDate = startDate.replace("-", "").substring(0, 8);
			endDate = endDate.replace("-", "").substring(0, 8);
		}
		
		logger.debug("startDate = {}", startDate);
		logger.debug("endDate = {}", endDate);
		
		String category = null;
		if (Util.checkStr(category1).equals("농지은행")) {
			category = category2;
		} else {
			category = category1;
		}
		logger.debug("categoryxxxxx = {}", category);

		List<String> branchList = makeBranchList(branches);

		int count = 0;
		
		for (String branch : branchList) {
			if(Util.isEmptyStr(startDate)){
				startDate = startDate + "000000";
			}
			if(Util.isEmptyStr(endDate)){
				endDate = endDate + "235959";
			}
			int customerCount = customerMapper.customerCount(category, branch, limit, startDate, endDate);
			logger.debug(branch + " 대상수 = {}", customerCount);
			count+= customerCount;
			logger.debug("누적 대상수 = {}", count);
		}

		return count;
	}

}
