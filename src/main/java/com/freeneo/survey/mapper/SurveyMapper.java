package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Survey;

public interface SurveyMapper {
	public List<Survey> list();
	public List<Survey> myList(String username);
	public Survey select(Long id);
	public Long selectRespondentCount(Long id);
	public void insert(Survey survey);
	public void update(Survey survey);
	public void delete(Long id);
	public List<Survey> selectByWriter(String writer);
	public List<Survey> selectByWriterAndDate(@Param("writer") String writer,
			@Param("startDate") String startDate, @Param("endDate") String endDate);
	public List<Survey> selectByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
	
}
