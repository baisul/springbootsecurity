package com.yl.springbootsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.springbootsecurity.bean.AuthenticationBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义一个认证拦截器，继承UsernamePasswordAuthenticationFilter，在登陆之前拦截到用户名和密码，并做相关处理
 * 主要是vue在调登录时，表单提交数据时，在userDetailServiceImpl的loadUserByUsername方法里的username参数拿不到前端传来的数据
 */
public class CustomizeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            //attempt Authentication when Content-Type is json
            if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){

                //use jackson to deserialize json
                ObjectMapper mapper = new ObjectMapper();
                UsernamePasswordAuthenticationToken authRequest = null;
                try (InputStream is = request.getInputStream()){
                    AuthenticationBean authenticationBean = mapper.readValue(is,AuthenticationBean.class);
                    System.out.println("atttemptAuthencation中数据:"+authenticationBean.getUsername() + authenticationBean.getPassword());
                    authRequest = new UsernamePasswordAuthenticationToken(
                            authenticationBean.getUsername(), authenticationBean.getPassword());
                }catch (IOException e) {
                    e.printStackTrace();
                    authRequest = new UsernamePasswordAuthenticationToken(
                            "", "");
                }finally {
                    setDetails(request, authRequest);
                    return this.getAuthenticationManager().authenticate(authRequest);
                }
            }

            //transmit it to UsernamePasswordAuthenticationFilter
            else {
                return super.attemptAuthentication(request, response);
            }

    }
}
