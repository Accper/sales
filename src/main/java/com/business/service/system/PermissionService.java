package com.business.service.system;

import com.business.bean.Permission;
import com.business.mapper.PermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description: permission service
 * @Date: 17:07 2018/8/10
 */
@Service
public class PermissionService {
    private final static Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 根据userid 查询一级菜单
     *
     * @param userId 用户id
     * @return 菜单/可用资源
     * @author taoye
     */
    public List<Permission> selectMenuListByUserId(Long userId) {
        if (userId == null) {
            logger.info("[<!-- user id is null --!>]");
            return null;
        }

        return permissionMapper.selectMenuListByUserId(userId);
    }

    /**
     * 根据userid, pid 查询二级菜单
     *
     * @param userId 用户id
     * @param pid    一级菜单id
     * @return 菜单/可用资源
     * @author taoye
     */
    public List<Permission> selectSecondLevelMenuListByUserIdAndPid(Long userId, Long pid) {
        if (userId == null || pid == null) {
            logger.info("[<!-- user id or pid is null, userId: {}, pid: {} --!>]", userId, pid);
            return null;
        }

        return permissionMapper.selectSecondLevelMenuListByUserIdAndPid(userId, pid);
    }

}
