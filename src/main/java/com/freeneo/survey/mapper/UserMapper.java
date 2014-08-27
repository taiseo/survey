package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.User;

public interface UserMapper {
    List<User> list();
    User select(User user);
    int insert(User user);
    int update(User user);
    int delete(User user);
}
