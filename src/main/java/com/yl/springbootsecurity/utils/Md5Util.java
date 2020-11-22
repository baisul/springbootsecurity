package com.yl.springbootsecurity.utils;

import org.springframework.util.DigestUtils;

/**
 * md5加密解密工具类
 */
public class Md5Util {

    //加密
    public static String encode(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    //匹配是否正确
    public static boolean isMatch(String ostr,String nstr) {
        if (encode(ostr).equals(nstr)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(encode("root"));
        System.out.println(isMatch("123","202cb962ac59075b964b07152d234b70"));
    }
}
