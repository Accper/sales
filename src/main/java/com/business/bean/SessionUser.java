package com.business.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author: taoye
 * @Description:
 * @Date: 16:05 2018/8/15
 */
public class SessionUser extends User {

    /**
     *
     */
    private static final long serialVersionUID = 7952640342073434532L;
    private SysUser sysUser;

    public SessionUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
