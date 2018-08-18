package com.business.service.system;

import com.business.Application;
import com.business.bean.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: taoye
 * @Description:
 * @Date: 17:45 2018/8/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SysUserServiceTest {

    @Resource
    private SysUserService sysUserService;

    @Test
    public void selectByUserName() {
        SysUser sysUser = sysUserService.selectByUserName("taoye");
        System.out.println(sysUser);
    }
}