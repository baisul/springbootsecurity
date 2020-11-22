package com.yl.springbootsecurity.config;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.springbootsecurity.utils.Constant;
import com.yl.springbootsecurity.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登陆成功处理器
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

//	@Autowired
//	@Qualifier("userDetailServiceImpl")
//	private UserDetailsService userDetailsService;

	@Autowired
	private ObjectMapper objectMapper;



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
		//处理中文乱码
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

		//得到用户
		User user = (User)authentication.getPrincipal();
		//这里可以做一些逻辑处理，比如，要返回这个用户的菜单权限到前端，前端拿到后，动态加载显示

		//返回数据
		ResultModel resultModel = new ResultModel(Constant.SUCCESS_CODE,"查找成功",Constant.OPERATE_SUCCESS,user);

		//以流的方式写数据到前端
		response.getWriter().write(JSON.toJSONString(resultModel));
	}

}
