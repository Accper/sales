package com.business.controller;

import com.business.bean.SessionUser;
import com.business.bean.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: taoye
 * @Description: common controller
 * @Date: 10:41 2018/8/13
 */
public class CommonController {
    protected SysUser getUserInfoBySession() {
        SessionUser userDetails;
        try {
            userDetails = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userDetails.getSysUser();
    }

    /**
     * 获取登陆用户ID
     *
     * @author zhoujl
     */
    protected Long getLoginUserId() {
        SysUser user = this.getUserInfoBySession();
        return user == null ? null : user.getId();
    }
}
