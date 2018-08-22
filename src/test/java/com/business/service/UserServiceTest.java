package com.business.service;

import com.business.Application;
import com.business.bean.SysUser;
import com.business.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void bindUserRole() {
        Integer integer = userService.bindUserRole(48L, 1L);
        System.out.println("结果为：" + integer);
    }

    @Test
    public void checkByPhone() {
        Boolean sysUser = userService.checkByEmail("123123@qq.com",69L);
        System.out.println("结果为："+sysUser);
    }

    @Test
    public void selectUserInfoByPrimaryKey() {
        UserDTO userDTO = userService.selectUserInfoByPrimaryKey(15L);
        if (userDTO == null) {
            System.out.println("空指针");
            return;
        }
        System.out.println(userDTO.getEmail() + " " + userDTO.getTitle());
        System.out.println(userDTO);
    }
}