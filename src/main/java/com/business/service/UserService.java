package com.business.service;

import com.business.bean.SearchParam;
import com.business.dto.UserDTO;
import com.business.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
