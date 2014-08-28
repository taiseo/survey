package com.freeneo.survey.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeneo.survey.domain.User;
import com.freeneo.survey.mapper.UserMapper;

@Service
public class UserService {
	
	static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserMapper userMapper;
	
	public List<User> getUsers() {
		return userMapper.list();
	}
	
	public User getUser(User user){
		return userMapper.select(user);
	}

	public User getUserById(User user){
		return userMapper.selectById(user);
	}
	
	public int insertUser(User user){
		return userMapper.insert(user);
	}
	
	public int updateUser(User user){
		return userMapper.update(user);
	}
	
	public int deleteUser(User user){
		return userMapper.delete(user);
	}
	
}
