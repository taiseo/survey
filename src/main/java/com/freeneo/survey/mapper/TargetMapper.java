package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.Target;

public interface TargetMapper {
	public List<Target> list();
	public List<Target> listBySurveyId(Long surveyId);
	public Target select(Long surveyId, String cstNo);
	public void insertAll(@Param("surveyId") Long surveyId, @Param("customers") List<Customer> customers);
	public void delete(Long surveyId);
	public List<Target> selectBySurveyId(Long surveyId);
	public int countBySurveyId(Long surveyId);
	public int countRespondentBySurvyeId(Long surveyId);
	public int countBySurveyIdAndBranch(@Param("surveyId") Long surveyId, @Param("branch") String branch);
	public int countRespondentBySurvyeIdAndBranch(@Param("surveyId") Long surveyId, @Param("branch") String branch);
	public List<Target> listBySurveysAndBranches(@Param("surveyList") List<Survey> surveyList,
			@Param("branchList") List<String> branchList);
}
