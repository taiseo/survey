package com.freeneo.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.UserMapper;

@Service
public class AuthService {

	@Autowired
	UserMapper userMapper;
	
	public User login(User user){
		return userMapper.select(user);
	}
}
