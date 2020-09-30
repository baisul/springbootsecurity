package com.yl.springbootsecurity.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.springbootsecurity.utils.Constant;
import com.yl.springbootsecurity.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登陆失败处理器
 */
public class LoginFailHandler implements AuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
		ResultModel resultModel;
		//用户不存在
		if (e instanceof InternalAuthenticationServiceException) {
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.USER_NOT_EXIT,Constant.OPERATE_ERROR);
			//密码错误
		} else if (e instanceof BadCredentialsException) {
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.USER_PASSWORD_ERROR,Constant.OPERATE_ERROR);
		} else {
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.COMMON_ERROR,Constant.OPERATE_ERROR);
		}

		response.setContentType("text/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(resultModel));
	}

}
