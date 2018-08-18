package com.business.service.system;

import com.business.bean.SysUser;
import com.business.common.CommonConstant;
import com.business.common.DbConstant;
import com.business.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description: sysUser service
 * @Date: 17:06 2018/8/10
 */
@Service
public class SysUserService {
    private final static Logger logger = LoggerFactory.getLogger(SysUserService.class);
    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser selectByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            logger.info("[<!-- userName is null --!>]");
            return null;
        }

        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userName);
        criteria.andEqualTo(DbConstant.STATUS, CommonConstant.YES);

        List<SysUser> list = sysUserMapper.selectByExample(example);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
