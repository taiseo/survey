package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Survey;

public interface SurveyMapper {
	public List<Survey> list();
	public List<Survey> myList(String username);
	public Survey select(Long id);
	public void insert(Survey survey);
	public void update(Survey survey);
	public void delete(Long id);
	public List<Survey> selectByWriter(String writer);
	public List<Survey> selectByWriterAndDate(@Param("writer") String writer,
			@Param("startDate") String startDate, @Param("endDate") String endDate);
	public List<Survey> selectByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * RESPONSES 테이블에서 select distinct로 가져온다.
	 * RESPONDENTS 테이블을 이용하는 건 reponseMapper에 있다.
	 * 이 함수보다는 responseMapper에 있는 countRespondentBySurveyId를 사용하기를 권한다.
	 * @param id
	 * @return
	 */
	public Long selectRespondentCount(Long id);
	public List<Survey> listByBranch(String branch);
	public List<Survey> listByBranchAndDates(
			@Param("branch") String branch, 
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	public List<Survey> listByUsernameAndDates(
			@Param("username") String username, 
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
}
