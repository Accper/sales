package com.business.controller;

import com.business.bean.User;
import com.business.common.ResponseBean;
import com.business.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: taoye
 * @Description: usercontroller
 * @Date: 10:23 2018/8/8
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public ResponseBean<User> selectByPrimaryKey(User user){
        ResponseBean<User> result = new ResponseBean<>();
        result.setStatus(1);
        result.setMessage("success");
        result.setData(userService.selectByPrimaryKey(user));
        return result;
    }
}
