package com.yl.springbootsecurity.controller;

import com.yl.springbootsecurity.entity.User;
import com.yl.springbootsecurity.service.UserService;
import com.yl.springbootsecurity.utils.Constant;
import com.yl.springbootsecurity.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户表控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //属性注入
    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/getUsers")
    public ResultModel getUsers() {
        List<User> users = this.userService.getUsers();
        ResultModel resultModel;
        if (users.size() > 0) {
            resultModel = new ResultModel(Constant.SUCCESS_CODE,"查找成功",true,users);
        } else {
            resultModel = new ResultModel(Constant.SUCCESS_CODE,"暂无数据",true,null);
        }
        return resultModel;
    }

    /**
     * 根据用户ID删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUserById")
    public ResultModel deleteUserById(@RequestParam(required = true) Integer id){
        ResultModel resultModel;
        Integer result = this.userService.deleteUserById(id);
        if (result > 0) {
            resultModel = new ResultModel(200,"删除成功");
        } else {
            resultModel = new ResultModel(0,"删除失败");
        }
        return resultModel;
    }
}
