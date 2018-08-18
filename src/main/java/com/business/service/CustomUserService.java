package com.business.service;

import com.business.bean.Permission;
import com.business.bean.SessionUser;
import com.business.bean.SysUser;
import com.business.mapper.PermissionMapper;
import com.business.service.system.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: taoye
 * @Description: CustomUserService 用于security
 * @Date: 15:35 2018/8/10
 */
@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = sysUserService.selectByUserName(username);
        if (user != null) {
            List<Permission> permissions = permissionMapper.selectByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && StringUtils.isNotBlank(permission.getName())) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            SessionUser sessionuser = new SessionUser(user.getUserName(), user.getPassword(), grantedAuthorities);
            sessionuser.setSysUser(user);
            return sessionuser;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
