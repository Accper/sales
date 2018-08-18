package com.business.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: taoye
 * @Description: 页面跳转
 * @Date: 15:37 2018/8/15
 */
@Controller
public class McvController {

    @RequestMapping(value = "/user/view")
    public String userView() {
        return "user/user";
    }

    @RequestMapping(value = "/role/view")
    public String roleView() {
        return "role/role";
    }

    @RequestMapping(value = "/permission/view")
    public String permissionView() {
        return "permission/permission";
    }
}
