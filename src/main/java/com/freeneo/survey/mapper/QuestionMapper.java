package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Question;

public interface QuestionMapper {
	public List<Question> list(Long surveyId);
	public Question select(Long id);
	public void insert(Question question);
	public void update(Question question);
	public void delete(Long id);
	public Long selectRespondentCount(Long id);
	public Long selectRespondentCountByBranch(@Param("questionId") Long questionId, @Param("branch") String branch);
	public String[] selectResponses(Long id);
	public String[] selectResponsesByBranch(@Param("id") Long id, @Param("branch") String branch);
}
