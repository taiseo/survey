package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.ResponseItem;

public interface ResponseItemMapper {
	public List<ResponseItem> list();
	public ResponseItem select(int id);
	public int insert(ResponseItem response);
	public int update(ResponseItem response);
	public int delete(int id);
}
