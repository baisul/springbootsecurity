package com.yl.springbootsecurity.service;

import com.yl.springbootsecurity.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 获取用户列表信息
     * @return
     */
    List<User> getUsers();

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    Integer deleteUserById(Integer id);
}
