package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.Response;

public interface ResponseMapper {
	public List<Response> list();
	public Response select(int id);
	public int insert(Response response);
	public int update(Response response);
	public int delete(int id);
}
