package com.freeneo.survey.mapperCrm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Customer;

public interface CustomerMapper {
	public List<Customer> list();  
	public void insert(Customer customer);
	public List<String> projectList();
	public List<String> branchList(String category);
	public List<Customer> customerList(@Param("category") String category, @Param("branchList") List<String> branchList);
}
