package com.business.mapper;

import com.business.bean.Permission;
import com.business.utils.base.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends IBaseMapper<Permission> {
    /**
     * 根据userid 查询所有权限菜单
     *
     * @param userId 用户id
     * @return 菜单/可用资源
     * @author zhoujl
     */
    List<Permission> selectByUserId(Long userId);


    /**
     * 根据userid 查询一级菜单
     *
     * @param userId 用户id
     * @return 菜单/可用资源
     * @author zhoujl
     */
    List<Permission> selectMenuListByUserId(@Param("userId") Long userId);

    /**
     * 根据userid, pid 查询二级菜单
     *
     * @param userId 用户id
     * @param pid    一级菜单id
     * @return 菜单/可用资源
     * @author zhoujl
     */
    List<Permission> selectSecondLevelMenuListByUserIdAndPid(@Param("userId") Long userId, @Param("pid") Long pid);
}