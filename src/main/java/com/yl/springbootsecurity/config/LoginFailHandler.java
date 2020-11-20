package com.yl.springbootsecurity.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.springbootsecurity.utils.Constant;
import com.yl.springbootsecurity.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
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

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
		ResultModel resultModel;
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

		if (e instanceof InternalAuthenticationServiceException) {
			//用户不存在
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.USER_NOT_EXIT,Constant.OPERATE_ERROR);
		} else if (e instanceof BadCredentialsException) {
			//密码错误
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.USER_PASSWORD_ERROR,Constant.OPERATE_ERROR);
		} else if (e instanceof AccountExpiredException) {
			//账号过期
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.ACCOUNT_PASS_TIME,Constant.OPERATE_ERROR);
		} else if (e instanceof CredentialsExpiredException) {
			//密码过期
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.USER_PASSWORD_PASS_TIME,Constant.OPERATE_ERROR);
		} else if (e instanceof DisabledException) {
			//账号不可用
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.ACCOUNT_NOT_USE,Constant.OPERATE_ERROR);
		} else if (e instanceof LockedException) {
			//账号锁定
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.ACCOUNT_LOCK,Constant.OPERATE_ERROR);
		}else {
			resultModel = new ResultModel(Constant.FAIL_CODE,Constant.COMMON_ERROR,Constant.OPERATE_ERROR);
		}
		response.getWriter().write(JSON.toJSONString(resultModel));
	}

}
