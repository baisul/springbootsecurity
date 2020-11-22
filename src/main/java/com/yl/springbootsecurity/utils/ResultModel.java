package com.yl.springbootsecurity.utils;

import java.io.Serializable;

/**
 * 公用的结果返回
 */
public class ResultModel implements Serializable {

    /**
     * 状态码：200成功，0失败
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 操作是否成功标志
     */
    private Boolean flag;
    /**
     * 返回数据
     */
    private Object data;


    public ResultModel(Integer code) {
        this.code = code;
    }

    public ResultModel(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(Integer code, String msg, Boolean flag) {
        this.code = code;
        this.msg = msg;
        this.flag = flag;
    }

    public ResultModel(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultModel(Integer code, String msg, Boolean flag, Object data) {
        this.code = code;
        this.msg = msg;
        this.flag = flag;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", flag=" + flag +
                ", data=" + data +
                '}';
    }
}
