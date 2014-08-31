package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Question;

public interface QuestionMapper {
	public List<Question> list();
	public Question select(int id);
	public void insert(Question question);
	public void update(Question question);
	public void delete(int id);
}
