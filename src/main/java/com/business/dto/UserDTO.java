package com.business.dto;

import com.business.bean.SysUser;

/**
 * @Author: taoye
 * @Description:
 * @Date: 14:51 2018/8/18
 */
public class UserDTO extends SysUser {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "title='" + title + '\'' +
                '}';
    }
}
