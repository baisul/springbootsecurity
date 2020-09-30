package com.yl.springbootsecurity.service.impl;

import com.yl.springbootsecurity.entity.User;
import com.yl.springbootsecurity.mapper.UserMapper;
import com.yl.springbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    //属性注入
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public Integer deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }
}
