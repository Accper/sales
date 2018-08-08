package com.business.service;

import com.business.bean.User;
import com.business.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description: userservice
 * @Date: 10:21 2018/8/8
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User selectByPrimaryKey(User user){
        return userMapper.selectByPrimaryKey(user);
    }
}
