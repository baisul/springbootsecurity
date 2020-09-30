package com.yl.springbootsecurity.service;

import com.yl.springbootsecurity.entity.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 根据用户ID获取权限列表
     * @param userId
     * @return
     */
    List<Permission> getPermissionsByUserId(Integer userId);

    /**
     * 根据菜单路径去获取权限列表
     * @param path
     * @return
     */
    List<Permission> selectListByPath(String path);
}
