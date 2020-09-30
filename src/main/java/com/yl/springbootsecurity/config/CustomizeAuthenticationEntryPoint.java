package com.yl.springbootsecurity.config;

import com.alibaba.fastjson.JSON;
import com.yl.springbootsecurity.utils.Constant;
import com.yl.springbootsecurity.utils.ResultModel;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问无权限时的异常
 */

@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultModel resultModel = new ResultModel(Constant.FAIL_CODE,Constant.USER_NOT_LOGIN);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(resultModel));
    }
}
