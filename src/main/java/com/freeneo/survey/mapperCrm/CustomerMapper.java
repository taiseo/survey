package com.freeneo.survey.mapperCrm;

import java.util.List;

import com.freeneo.survey.domain.Customer;

public interface CustomerMapper {
	public List<Customer> list();  
	public void insert(Customer customer);  
}
