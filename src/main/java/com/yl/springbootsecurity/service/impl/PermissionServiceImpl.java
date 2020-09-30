package com.yl.springbootsecurity.service.impl;

import com.yl.springbootsecurity.entity.Permission;
import com.yl.springbootsecurity.mapper.PermissionMapper;
import com.yl.springbootsecurity.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionsByUserId(Integer userId) {
        return permissionMapper.getPermissionsByUserId(userId);
    }

    @Override
    public List<Permission> selectListByPath(String path) {
        return permissionMapper.selectListByPath(path);
    }
}
