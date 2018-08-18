package com.business.controller;

import com.business.bean.PageResponseBean;
import com.business.bean.SearchParam;
import com.business.bean.SysUser;
import com.business.common.CodeConstant;
import com.business.common.MessageConstant;
import com.business.common.ResponseBean;
import com.business.dto.UserDTO;
import com.business.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
