//package com.yl.springbootsecurity.service.impl;
//
//import com.yl.springbootsecurity.entity.Permission;
//import com.yl.springbootsecurity.entity.User;
//import com.yl.springbootsecurity.service.PermissionService;
//import com.yl.springbootsecurity.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 这个UserDetailServiceImpl,实现了security核心包下的UserDetailService接口，并重写loadUserByUsername
// * 我们可以在这里自定义用户认证逻辑，username参数即为登陆时要传过来的用户名
// */
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//    //属性注入
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PermissionService permissionService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (username == null || "".equals(username)) {
//            throw new RuntimeException("用户名不能为空");
//        }
//        //根据用户名获取用户
//        User user = this.userService.getUserByUserName(username);
//        if (user == null) {
//            throw new RuntimeException("用户不存在");
//        }
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        if (user != null) {
//            //根据用户ID，获取用户的权限列表
//            List<Permission> permissions = this.permissionService.getPermissionsByUserId(user.getId());
//            //声明用户授权
//            permissions.forEach(item -> {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(item.getCode());
//                grantedAuthorities.add(grantedAuthority);
//            });
//        }
//        //最后要返回一个User一个对象
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),passwordEncoder.encode(user.getPassword()),grantedAuthorities);
//    }
//}
