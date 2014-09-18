package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.SVLog;

public interface SVLogMapper {
	public List<SVLog> list();
	public SVLog selectById(String id);
	public void insert(SVLog log);
	public void update(SVLog log);
}
