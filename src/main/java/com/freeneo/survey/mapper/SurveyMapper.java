package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Survey;

public interface SurveyMapper {
	public List<Survey> list();
	public Survey select(Long id);
	public void insert(Survey survey);
	public void update(Survey survey);
	public void delete(Long id);
}
