package com.business.service.system;

import com.business.Application;
import com.business.bean.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description:
 * @Date: 11:18 2018/8/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PermissionServiceTest {

    @Resource
    private PermissionService permissionService;
    @Test
    public void selectMenuListByUserId() {
        List<Permission> permissions = permissionService.selectMenuListByUserId(1L);
        System.out.println(permissions);
    }

    @Test
    public void selectSecondLevelMenuListByUserIdAndPid() {
        List<Permission> permissions = permissionService.selectSecondLevelMenuListByUserIdAndPid(1L, 12L);
        System.out.println(permissions);
    }
}