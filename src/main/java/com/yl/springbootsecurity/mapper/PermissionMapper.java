package com.yl.springbootsecurity.mapper;

import com.yl.springbootsecurity.entity.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> getPermissionsByUserId(Integer userId);

    List<Permission> selectListByPath(String path);
}
