package com.freeneo.survey.mapperCrm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Customer;

public interface CustomerMapper {
	public List<Customer> list();
	public void insert(Customer customer);
	public List<String> projectList();
	public List<String> branchList(@Param("category") String category);
	public List<Customer> customerList(
			@Param("category") String category, 
			@Param("branch") String branch,
			@Param("limit") int limit);
	public List<Customer> customerListByDate(
			@Param("category") String category, 
			@Param("branch") String branch,
			@Param("limit") int limit,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate
			);
	public int customerCount(
			@Param("category") String category, 
			@Param("branch") String branch,
			@Param("limit") int limit,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate
			);
}
