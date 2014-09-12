package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Response;

public interface ResponseMapper {
	public List<Response> list();
	public List<Response> selectByQuestionId(Long questionId);
	public Response select(Long id);
	public Response selectByQuestionIdAndRespondent(Response response);
	public void insert(Response response);
	public void update(Response response);
	public void delete(Long id);
	public void deleteByQuestionIdAndRespondent(Response response);
}
