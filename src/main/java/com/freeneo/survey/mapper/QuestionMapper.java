package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Question;

public interface QuestionMapper {
	public List<Question> list();
	public Question select(int id);
	public int insert(Question question);
	public int update(Question question);
	public int delete(int id);
}
