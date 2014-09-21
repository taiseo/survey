package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Survey;
import com.freeneo.survey.domain.Target;
import com.freeneo.survey.domain.TargetGroup;

public interface TargetGroupMapper {
	public List<TargetGroup> list();
	public TargetGroup select(Long id);
	public void insert(TargetGroup targetGroup);
	public void update(TargetGroup targetGroup);
	public void delete(Long id);
}
