package com.business.service;

import com.business.Application;
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
        System.out.println("结果为："+integer);
    }
}