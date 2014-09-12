package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Question;

public interface QuestionMapper {
	public List<Question> list(Long surveyId);
	public Question select(Long id);
	public void insert(Question question);
	public void update(Question question);
	public void delete(Long id);
	public Long selectRespondentCount(Long id);
	public String[] selectResponses(Long id);
}
