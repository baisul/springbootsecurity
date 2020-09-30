package com.yl.springbootsecurity.filter;

import com.yl.springbootsecurity.entity.Permission;
import com.yl.springbootsecurity.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 安全元数据源FilterInvocationSecurityMetadataSource，主要是拦截到请求路径，做相关处理
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private PermissionService permissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println(requestUrl);
        List<Permission> permissionList;
        if (!requestUrl.contains("?")) {
            permissionList =  permissionService.selectListByPath(requestUrl);
        } else {
            String [] arrs = requestUrl.split("\\?");
           permissionList =  permissionService.selectListByPath(arrs[0]);
            System.out.println(arrs[0]);
            System.out.println(arrs[1]);
        }
        //查询具体某个接口的权限
//        List<Permission> permissionList =  permissionService.selectListByPath(arrs[0]);
        System.out.println(permissionList.size());
        if(permissionList == null || permissionList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for(int i = 0;i<permissionList.size();i++){
            attributes[i] = permissionList.get(i).getCode();
        }
        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
