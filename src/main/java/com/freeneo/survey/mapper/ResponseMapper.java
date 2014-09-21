package com.freeneo.survey.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.Survey;

public interface ResponseMapper {
	public List<Response> list();
	public List<Response> selectByQuestionId(Long questionId);
	public Response select(Long id);
	public Response selectByQuestionIdAndRespondent(Response response);
	public void insert(Response response);
	public void update(Response response);
	public void delete(Long id);
	public void deleteByQuestionIdAndRespondent(Response response);
	
	public int countRespondentById(String id);
	public void insertRespondent(@Param("id") String id, @Param("bonbu") String bonbu, @Param("branch") String branch, @Param("surveyId") Long surveyId);
	public void updateRespondent(@Param("id") String id, @Param("bonbu") String bonbu, @Param("branch") String branch, @Param("surveyId") Long surveyId);
	public List<Map<String, ?>> countRespondentBySurveysAndBranches(@Param("surveyList") List<Survey> surveyList,
			@Param("branchList") List<String> branchList);
}
