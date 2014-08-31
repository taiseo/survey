package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Survey;

public interface SurveyMapper {
	public List<Survey> list();
	public Survey select(Long id);
	public Long insert(Survey survey);
	public Long update(Survey survey);
	public Long delete(Long id);
}
