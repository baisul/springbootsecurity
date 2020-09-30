package com.yl.springbootsecurity.entity;

import java.io.Serializable;

/**
 * 权限表实体类
 */
public class Permission implements Serializable {
    private Integer id;
    /**
     * 权限编码
     */
    private String code;
    /**
     * 描述
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
