package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Response;

public interface ResponseMapper {
	public List<Response> list();
	public Response select(Long id);
	public List<Response> selectByQuestionIdAndRespondent(Response response);
	public void insert(Response response);
	public void update(Response response);
	public void delete(Long id);
}
