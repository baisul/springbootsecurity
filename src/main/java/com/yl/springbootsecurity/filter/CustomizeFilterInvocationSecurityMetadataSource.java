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
        List<Permission> permissionList;
        //根据请求地址获取该地址有哪一些权限才能够访问
        //不是get请求的情况，直接查数据
        if (!requestUrl.contains("?")) {
            permissionList =  permissionService.selectListByPath(requestUrl);
        } else {
            //为get请求的情况下，要切割字符串，因为?后面带的是一些参数，我们在数据库保存的仅仅是请求地址
            String [] arrs = requestUrl.split("\\?");
           permissionList =  permissionService.selectListByPath(arrs[0]);
        }
        if(permissionList == null || permissionList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        //如果当前请求地址需要权限才能够访问
        String[] attributes = new String[permissionList.size()];
        for(int i = 0;i<permissionList.size();i++){
            attributes[i] = permissionList.get(i).getCode();
        }
        //调用Securtity的createList方法，构造出一个List<ConfigAttribute>的集合
        //因为这个集合在访问决策管理器要用到
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
