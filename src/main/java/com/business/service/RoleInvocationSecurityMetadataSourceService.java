package com.business.service;

import com.business.bean.Permission;
import com.business.mapper.PermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: taoye
 * @Description:
 * @Date: 18:21 2018/8/10
 */
@Service
public class RoleInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    private static final Logger logger = LoggerFactory.getLogger(RoleInvocationSecurityMetadataSourceService.class);
    @Resource
    private PermissionMapper permissionMapper;

    private Map<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载资源，初始化资源变量
     */
    public void loadResourceDefine() {
        logger.info("RoleInvocationSecurityMetadataSourceService ==> loadResourceDefine");
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<Permission> permissions = permissionMapper.selectAll();
        for (Permission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            array.add(cfg);
            map.put(permission.getUrl(), array);
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        logger.info("RoleInvocationSecurityMetadataSourceService ==> getAttributes");
        if (map == null) {
            loadResourceDefine();
        }
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public void setPermissionMap(List<Permission> permissions) {
        logger.info("RoleInvocationSecurityMetadataSourceService ==> setPermissionMap");
        Map<String, Collection<ConfigAttribute>> updatedMap = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        for (Permission permission : permissions) {
            logger.info("permission: {}", permission);
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            array.add(cfg);
            updatedMap.put(permission.getUrl(), array);
        }
        map.clear();
        map.putAll(updatedMap);
        logger.info("-----------------------update permission list end");
    }

}
