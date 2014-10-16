package com.freeneo.survey.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Response;
import com.freeneo.survey.domain.Survey;

public interface ResponseMapper {
	public List<Response> list();
	public List<Response> selectByQuestionId(Long questionId);
	public List<Response> selectByQuestionIdAndBranch(@Param("questionId") Long id, @Param("branch") String branch);
	public Response select(Long id);
	public Response selectByQuestionIdAndRespondent(Response response);
	public void insert(Response response);
	public void update(Response response);
	public void delete(Long id);
	public void deleteByQuestionIdAndRespondent(Response response);

	public int countRespondentById(String id);
	public void insertRespondent(@Param("id") String id, @Param("bonbu") String bonbu, @Param("branch") String branch, @Param("surveyId") Long surveyId);
	public void updateRespondent(@Param("id") String id, @Param("bonbu") String bonbu, @Param("branch") String branch, @Param("surveyId") Long surveyId);
	public Long countRespondentBySurveyId(Long surveyId);
	public Long countRespondentBySurveyIdAndBranch(@Param("surveyId") Long surveyId, @Param("branch") String branch);
	public List<Response> listFullBySurveyId(Long surveyId);
	
	/**
	 * 기간에 상관없이 총 설문에서 해당 지사들의 응답수를 가져온다. 날짜 옵션이 없어서 결과적으로 사용하지 않게 된 함수.
	 * @param surveyList
	 * @param branchList
	 * @return
	 */
	public List<Map<String, ?>> countRespondentBySurveysAndBranches(@Param("surveyList") List<Survey> surveyList,
			@Param("branchList") List<String> branchList);	
}
