package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.SVLog;

public interface SVLogMapper {
	public List<SVLog> list();
	public SVLog selectById(String id);
	public void insert(SVLog log);
	public void update(SVLog log);
	public SVLog selectByIdAndUsername(@Param("id") String id, @Param("username") String username);
}
