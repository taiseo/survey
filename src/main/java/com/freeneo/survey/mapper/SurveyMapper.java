package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Survey;

public interface SurveyMapper {
	public List<Survey> list();
	public Survey select(Long id);
	public int insert(Survey survey);
	public int update(Survey survey);
	public int delete(Long id);
}
