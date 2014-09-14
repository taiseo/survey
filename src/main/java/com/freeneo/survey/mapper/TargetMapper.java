package com.freeneo.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Customer;
import com.freeneo.survey.domain.Target;

public interface TargetMapper {
	public List<Target> list();
	public List<Target> listBySurveyId(Long surveyId);
	public Target select(Long surveyId, String cstNo);
	public void insertAll(@Param("surveyId") Long surveyId, @Param("customers") List<Customer> customers);
	public void delete(Long surveyId);
}
