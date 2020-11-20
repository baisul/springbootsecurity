//package com.yl.springbootsecurity.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yl.springbootsecurity.bean.AuthenticationBean;
//import com.yl.springbootsecurity.entity.Permission;
//import com.yl.springbootsecurity.service.PermissionService;
//import com.yl.springbootsecurity.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义一个认证拦截器，继承UsernamePasswordAuthenticationFilter，在登陆之前拦截到用户名和密码，并做相关处理
// * 主要是vue在调登录时，表单提交数据时，在userDetailServiceImpl的loadUserByUsername方法里的username参数拿不到前端传来的数据
// */
//public class CustomizeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    @Autowired
//    private PermissionService permissionService;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        //从前端获取用户名和密码
//        String username = this.obtainUsername(request);
//        String password = this.obtainPassword(request);
//        System.out.println(username);
//        System.out.println(password);
//        //定义UserDetails对象，构造再返回
//        UserDetails userDetails = null;
//        com.yl.springbootsecurity.entity.User user = this.userService.getUserByUserName(username);
//        if (user == null) {
//            throw new InternalAuthenticationServiceException("用户不存在");
//        } else {
//            //根据用户名获取权限列表
//            List<Permission> permissions = permissionService.getPermissionsByUserId(user.getId());
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            if (permissions != null && permissions.size() > 0) {
//                permissions.stream().forEach(item -> {
//                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(item.getCode());
//                    authorities.add(grantedAuthority);
//                });
//            }
//            //构造UserDetails对象
//            userDetails = new User(username, user.getPassword(), authorities);
//            UsernamePasswordAuthenticationToken result = null;
//            if (!password.equals(user.getPassword())) {
//                throw new BadCredentialsException("密码错误");
//            } else {
//                //返回UsernamePasswordAuthenticationToken对象
//                result = new UsernamePasswordAuthenticationToken(userDetails,password, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
//                result.setAuthenticated(false);
//                this.setDetails(request,result);
//            }
//            return this.getAuthenticationManager().authenticate(result);
//        }
//    }
//}
