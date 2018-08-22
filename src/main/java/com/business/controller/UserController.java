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
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.ws.Response;
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

    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean<Integer> userLockOrUnlock(Long[] ids) {
        ResponseBean<Integer> result = new ResponseBean<>();
        if (ids == null) {
            logger.info("the ids is null");
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.PARAM_ERRPR);
            return result;
        }
        Integer data = userService.userLockOrUnlock(ids);
        if (data == null) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        result.setStatus(CodeConstant.SUCCESS);
        result.setMessage(MessageConstant.SELECT_SUCCESS);
        result.setData(data);
        return result;
    }

    @RequestMapping(value = "select/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean<UserDTO> selectUserInfoById(@PathVariable(value = "id") Long id) {
        ResponseBean<UserDTO> result = new ResponseBean<>();
        if (id == null) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        UserDTO userDTO = userService.selectUserInfoByPrimaryKey(id);
        if (userDTO == null) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        result.setStatus(CodeConstant.SUCCESS);
        result.setMessage(MessageConstant.SELECT_SUCCESS);
        result.setData(userDTO);
        return result;
    }

    /**
     * 修改用户信息
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<Integer> updateUserInfo(@RequestBody UserDTO userDTO) {
        ResponseBean<Integer> result = new ResponseBean<>();
        if (userDTO == null) {
            logger.info("UserController == > updateUserInfo the userDTO is null");
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.PARAM_ERRPR);
            return result;
        }
        // 前后密码不一致，则修改用户的密码
        SysUser user = userService.selectPasswordById(userDTO.getId());
        userDTO.setStatus(user.getStatus());
        if (!user.getPassword().equals(userDTO.getPassword())) {
            userDTO.setPassword(MD5Util.encode(userDTO.getPassword()));
        }
        Integer flag = userService.updateSysUser(userDTO);
        if (flag == null) {
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.FAILURE);
            return result;
        }
        result.setStatus(CodeConstant.SUCCESS);
        result.setMessage(MessageConstant.SELECT_SUCCESS);
        result.setData(flag);
        return result;
    }

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
        Boolean user = userService.checkByUserName(userDTO.getUserName(), userDTO.getId());
        if (!user) {
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
        logger.info("the user id is {}, the role id is {}", userDTO.getId(), userDTO.getRoleId());
        userService.bindUserRole(userDTO.getId(), userDTO.getRoleId());
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
    @RequestMapping(value = "/check/username", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean checkByUserName(String userName, Long id) {
        ResponseBean result = new ResponseBean();
        if (StringUtils.isBlank(userName)) {
            logger.info("UserController == > checkByUserName the username is blank");
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.PARAM_ERRPR);
            return result;
        }
        Boolean sysUser = userService.checkByUserName(userName, id);
        if (sysUser) {
            // 查不到用户才能注册，才能注册
            logger.info("UserController == > checkByUserName can register a new user");
            result.setStatus(CodeConstant.SUCCESS);
            result.setMessage(MessageConstant.SELECT_SUCCESS);
            return result;
        }
        result.setStatus(CodeConstant.ERROR);
        result.setMessage(MessageConstant.USERNAME_ISEXIST);
        return result;
    }

    /**
     * 根据手机号码是否被注册绑定过
     *
     * @param phone 手机号码
     * @return
     */
    @RequestMapping(value = "/check/phone", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean checkByPhone(String phone, Long id) {
        ResponseBean result = new ResponseBean();
        if (StringUtils.isBlank(phone)) {
            logger.info("UserController == > checkByPhone the phone is blank");
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.PARAM_ERRPR);
            return result;
        }
        Boolean sysUser = userService.checkByPhone(phone, id);
        if (sysUser) {
            // 查不到用户才能注册,该手机号码才能使用.
            logger.info("UserController == > checkByPhone can register a new phone");
            result.setStatus(CodeConstant.SUCCESS);
            result.setMessage(MessageConstant.SELECT_SUCCESS);
            return result;
        }
        result.setStatus(CodeConstant.ERROR);
        result.setMessage(MessageConstant.USERNAME_ISEXIST);
        return result;
    }

    /**
     * 根据邮箱地址是否被注册绑定过
     *
     * @param sysUser 邮箱地址
     * @return
     */
    @RequestMapping(value = "/check/email", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean checkByEmail(@RequestBody SysUser sysUser) {
        ResponseBean result = new ResponseBean();
        if (StringUtils.isBlank(sysUser.getEmail())) {
            logger.info("UserController == > checkByEmail the email is blank");
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(MessageConstant.PARAM_ERRPR);
            return result;
        }
        Boolean user = userService.checkByEmail(sysUser.getEmail(), sysUser.getId());
        if (user) {
            // 查不到用户才能注册,该邮箱才能使用
            logger.info("UserController == > checkByEmail can register a new email");
            result.setStatus(CodeConstant.SUCCESS);
            result.setMessage(MessageConstant.SELECT_SUCCESS);
            return result;
        }
        result.setStatus(CodeConstant.ERROR);
        result.setMessage(MessageConstant.USERNAME_ISEXIST);
        return result;
    }
}
