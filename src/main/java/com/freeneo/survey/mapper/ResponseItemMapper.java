package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.ResponseItem;

public interface ResponseItemMapper {
	public List<ResponseItem> list(Long questionId);
	public ResponseItem select(Long id);
	public Long selectResponseItemCount(ResponseItem responseItem);
	public Long selectResponseItemCountByBranch(@Param("responseItem") ResponseItem responseItem, @Param("branch") String branch);
	public Long insert(ResponseItem responseItem);
	public Long update(ResponseItem responseItem);
	public Long delete(Long id);
}
