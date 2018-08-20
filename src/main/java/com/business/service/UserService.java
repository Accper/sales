package com.business.service;

import com.business.bean.Role;
import com.business.bean.SearchParam;
import com.business.bean.SysUser;
import com.business.dto.UserDTO;
import com.business.mapper.RoleMapper;
import com.business.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description: user service
 * @Date: 17:12 2018/8/15
 */
@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 查询所有的职务
     *
     * @return
     */
    public List<Role> selectRoles() {
        return roleMapper.selectAll();
    }

    /**
     * 注册用户
     *
     * @param sysUser 用户信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer registerUser(SysUser sysUser) {
        if (sysUser == null) {
            logger.info("UserService == > registerUser the user info is null");
            return null;
        }
        return sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 查询所有用户的信息
     *
     * @return
     */
    public List<UserDTO> selectAll(SearchParam searchParam) {
        logger.info("UserService ==> selectAll");
        if (searchParam.getPageNum() != null || searchParam.getPageSize() != null) {
            PageHelper.startPage(searchParam);
        }
        return sysUserMapper.selectAllUsers(searchParam.getKeyWord());
    }

    /**
     * 根据用户名查看该用户名是否已经被注册过
     *
     * @param userName 账号名
     * @return
     */
    public SysUser checkByUserName(String userName) {
        logger.info("UserService ==> selectAll");
        if (StringUtils.isBlank(userName)) {
            logger.info("UserService == > checkByUserName the username is blank");
            return null;
        }
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userName);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        if (sysUsers.isEmpty()) {
            return null;
        }
        return sysUsers.get(0);
    }
}
