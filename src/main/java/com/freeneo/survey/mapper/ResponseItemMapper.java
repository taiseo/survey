package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.ResponseItem;

public interface ResponseItemMapper {
	public List<ResponseItem> list(Long questionId);
	public ResponseItem select(Long id);
	public Long insert(ResponseItem response);
	public Long update(ResponseItem response);
	public Long delete(Long id);
}
