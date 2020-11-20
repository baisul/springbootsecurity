package com.yl.springbootsecurity.config;
import com.yl.springbootsecurity.entity.Permission;
import com.yl.springbootsecurity.service.PermissionService;
import com.yl.springbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义认证提供者
 * @author Administrator
 */
@Service
public class CustomizeAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取前端传过来的的username和password
        String username = authentication.getName();
        String prepassword = (String)authentication.getCredentials();
        //定义UserDetails对象，构造再返回
        UserDetails userDetails = null;
        com.yl.springbootsecurity.entity.User user = this.userService.getUserByUserName(username);
        if (user == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
            //根据用户名获取权限列表
            List<Permission> permissions= permissionService.getPermissionsByUserId(user.getId());
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (permissions != null && permissions.size() > 0) {
                permissions.stream().forEach(item -> {
                    GrantedAuthority grantedAuthority= new SimpleGrantedAuthority(item.getCode());
                    authorities.add(grantedAuthority);
                });
            }
            //构造UserDetails对象
            userDetails = new User(username, user.getPassword(), authorities);
            if (authentication.getCredentials() == null) {
                throw new BadCredentialsException("登录名或密码错误");
            } else if (!prepassword.equals(user.getPassword())) {
                throw new BadCredentialsException("登录名或密码错误");
            }
                //返回UsernamePasswordAuthenticationToken对象
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
                return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}