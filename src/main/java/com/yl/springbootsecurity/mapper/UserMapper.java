package com.yl.springbootsecurity.mapper;

import com.yl.springbootsecurity.entity.User;

import java.util.List;

public interface UserMapper {

    User getUserByUserName(String username);

    List<User> getUsers();

    Integer deleteUserById(Integer id);
}
