package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.ResponseItem;

public interface ResponseItemMapper {
	public List<ResponseItem> list(Long questionId);
	public ResponseItem select(Long id);
	public Long selectResponseItemCount(ResponseItem responseItem);
	public Long insert(ResponseItem responseItem);
	public Long update(ResponseItem responseItem);
	public Long delete(Long id);
}
