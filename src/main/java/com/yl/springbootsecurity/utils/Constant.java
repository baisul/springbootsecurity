package com.yl.springbootsecurity.utils;

/**
 * 存放常量
 */
public interface Constant {
    /**
     * 状态成功码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 状态失败码
     */
    public static final int FAIL_CODE = 0;
    /**
     * 操作成功
     */
    public static final boolean OPERATE_SUCCESS = true;
    /**
     * 操作失败
     */
    public static final boolean OPERATE_ERROR = false;

    public static final String USER_NOT_LOGIN = "用户未登录，请先登录";
    public static final String USER_NOT_EXIT = "用户不存在";
    public static final String USER_PASSWORD_ERROR = "密码输入出错";
    public static final String COMMON_ERROR = "系统出错";
}
