package com.business.service;

import com.business.bean.Role;
import com.business.bean.Role2User;
import com.business.bean.SearchParam;
import com.business.bean.SysUser;
import com.business.dto.UserDTO;
import com.business.mapper.Role2UserMapper;
import com.business.mapper.RoleMapper;
import com.business.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @Resource
    private Role2UserMapper role2UserMapper;

    /**
     * 一键锁定和解锁用户
     *
     * @param ids
     * @return
     */
    public Integer userLockOrUnlock(Long[] ids) {
        if (ids == null) {
            return null;
        }
        Integer cout = 0;
        for (Long id : ids) {
            SysUser user = sysUserMapper.selectByPrimaryKey(id);
            if (user.getStatus().equals("0")) {
                // 如果已经被锁定了，操作就是解锁
                user.setStatus("1");
                sysUserMapper.updateByPrimaryKey(user);
                cout++;
            } else {
                // 如果已经是正常的了，那么接下来的操作就是锁定用户
                user.setStatus("0");
                sysUserMapper.updateByPrimaryKey(user);
                cout++;
            }
        }
        return cout;
    }

    /**
     * 根据用id查询用户信息
     *
     * @param id
     * @return
     */
    public UserDTO selectUserInfoByPrimaryKey(Long id) {
        if (id == null) {
            logger.info("UserService == > selectUserInfoByPrimaryKey the id is null");
            return null;
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        if (user == null) {
            logger.info("UserService == > selectUserInfoByPrimaryKey the user is null");
            return null;
        }
        // 根据userid查询角色
        Example example = new Example(Role2User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sysUserId", user.getId());
        List<Role2User> role2Users = role2UserMapper.selectByExample(example);
        if (role2Users.isEmpty()) {
            logger.info("UserService == > selectUserInfoByPrimaryKey the role2Users is isEmpty");
            return null;
        }
        Role role = roleMapper.selectByPrimaryKey(role2Users.get(0).getSysRoleId());
        if (role == null) {
            logger.info("UserService == > selectUserInfoByPrimaryKey the role is null");
            return null;
        }
        // 封装数据
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setFullName(user.getFullName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobile(user.getMobile());
        userDTO.setStatus(user.getStatus());
        userDTO.setTitle(role.getTitle());
        userDTO.setRoleId(role.getId());
        return userDTO;
    }

    /**
     * 修改用户的信息
     *
     * @param userDTO 用户信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysUser(UserDTO userDTO) {
        if (userDTO == null) {
            logger.info("UserService == > updateSysUser the uerDTO is null");
            return null;
        }
        Example example = new Example(Role2User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sysUserId", userDTO.getId());
        List<Role2User> role2Users = role2UserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(role2Users)) {
            return null;
        }
        Role2User role2User = new Role2User();
        role2User.setSysRoleId(userDTO.getRoleId());
        role2User.setSysUserId(userDTO.getId());
        role2User.setId(role2Users.get(0).getId());
        // 更新职务
        role2UserMapper.updateByPrimaryKey(role2User);
        return sysUserMapper.updateByPrimaryKey(userDTO);
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    public SysUser selectPasswordById(Long id) {
        if (id == null) {
            return null;
        }
        return sysUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 绑定用户的职务
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer bindUserRole(Long userId, Long roleId) {
        if (userId == null || roleId == null) {
            logger.info("UserService == > bindUserRole the userId or roleId is null");
            return null;
        }
        Role2User role2User = new Role2User();
        role2User.setSysUserId(userId);
        role2User.setSysRoleId(roleId);
        return role2UserMapper.insertSelective(role2User);
    }

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
    public Boolean checkByUserName(String userName, Long id) {
        logger.info("UserService ==> selectAll");
        if (StringUtils.isBlank(userName)) {
            logger.info("UserService == > checkByUserName the username is blank");
            return null;
        }
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userName);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        return CollectionUtils.isEmpty(sysUsers);
    }

    /**
     * 根据手机号码是否被绑定
     *
     * @param phone 手机号码
     * @return
     */
    public Boolean checkByPhone(String phone, Long id) {
        logger.info("UserService ==> checkByPhone");
        if (StringUtils.isBlank(phone)) {
            logger.info("UserService == > checkByUserName the phone is blank");
            return null;
        }
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", phone);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        return CollectionUtils.isEmpty(sysUsers);
    }


    /**
     * 根据邮箱地址是否被绑定
     *
     * @param email 邮件系统
     * @return
     */
    public Boolean checkByEmail(String email, Long id) {
        logger.info("UserService ==> checkByEmail");
        if (StringUtils.isBlank(email)) {
            logger.info("UserService == > checkByUserName the email is blank");
            return null;
        }
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", email);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        return CollectionUtils.isEmpty(sysUsers);
    }
}
