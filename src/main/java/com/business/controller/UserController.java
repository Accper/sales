package com.business.controller;

import com.business.bean.PageResponseBean;
import com.business.bean.Role;
import com.business.bean.SearchParam;
import com.business.bean.SysUser;
import com.business.common.CodeConstant;
import com.business.common.MessageConstant;
import com.business.common.ResponseBean;
import com.business.dto.UserDTO;
import com.business.service.UserService;
import com.business.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description: user controller
 * @Date: 17:15 2018/8/15
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 获取职务名称
     *
     * @return
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean<List<Role>> selectRoles() {
        ResponseBean<List<Role>> result = new ResponseBean<>();
        List<Role> roles = userService.selectRoles();
        if (roles.isEmpty()) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        result.setStatus(CodeConstant.SUCCESS);
        result.setMessage(MessageConstant.SELECT_SUCCESS);
        result.setData(roles);
        return result;
    }

    /**
     * 注册用户
     *
     * @param userDTO 用户的信息
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean registerUser(@RequestBody UserDTO userDTO) {
        ResponseBean result = new ResponseBean();
        if (userDTO == null) {
            logger.info("UserController ==> registerUser");
            result.setStatus(CodeConstant.NOT_EXIST);
            result.setMessage(MessageConstant.IS_NOT_EXIST);
            return result;
        }
        // 判断账号是否存在
        SysUser user = userService.checkByUserName(userDTO.getUserName());
        if (user != null) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.USERNAME_ISEXIST);
            return result;
        }
        // 密码不能为空
        if (StringUtils.isBlank(userDTO.getPassword())) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        // 密码加密
        userDTO.setPassword(MD5Util.encode(userDTO.getPassword()));
        // 新增用户
        Integer flag = userService.registerUser(userDTO);
        if (flag == null) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        // 绑定用户的职务
        logger.info("the user id is {}, the role id is {}",userDTO.getId(),userDTO.getRoleId());
        userService.bindUserRole(userDTO.getId(),userDTO.getRoleId());
        result.setStatus(CodeConstant.SUCCESS);
        result.setMessage(MessageConstant.SELECT_SUCCESS);
        return result;
    }

    /**
     * 查询所有用户的信息
     *
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public PageResponseBean<List<UserDTO>> selectAllUsers(SearchParam searchParam) {
        PageResponseBean<List<UserDTO>> result = new PageResponseBean<>();
        List<UserDTO> sysUsers = userService.selectAll(searchParam);
        if (sysUsers.isEmpty()) {
            logger.info("UserController ==> selectAllUsers");
            result.setStatus(CodeConstant.NOT_EXIST);
            result.setMessage(MessageConstant.IS_NOT_EXIST);
            return result;
        }
        result.setStatus(CodeConstant.SUCCESS);
        result.setMessage(MessageConstant.SELECT_SUCCESS);
        result.setData(sysUsers);
        return result;
    }

    /**
     * 根据用户名查看该用户名是否已经被注册过
     *
     * @param userName 账号名
     * @return
     */
    @RequestMapping(value = "/check/{username}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean checkByUserName(@PathVariable(value = "username") String userName) {
        ResponseBean result = new ResponseBean();
        if (StringUtils.isBlank(userName)) {
            logger.info("UserController == > checkByUserName the username is blank");
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.PARAM_ERRPR);
            return result;
        }
        SysUser sysUser = userService.checkByUserName(userName);
        if (sysUser == null) {
            // 查不到用户才能注册
            logger.info("UserController == > checkByUserName can register a new user");
            result.setStatus(CodeConstant.SUCCESS);
            result.setMessage(MessageConstant.SELECT_SUCCESS);
            return result;
        }
        result.setStatus(CodeConstant.ERROR);
        result.setMessage(MessageConstant.USERNAME_ISEXIST);
        return result;
    }
}
