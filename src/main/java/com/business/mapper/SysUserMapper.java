package com.business.mapper;

import com.business.bean.SysUser;
import com.business.dto.UserDTO;
import com.business.utils.base.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends IBaseMapper<SysUser> {
    List<UserDTO> selectAllUsers(@Param("keyWord") String keyWord);
}