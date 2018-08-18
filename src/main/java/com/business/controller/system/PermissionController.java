package com.business.controller.system;

import com.business.bean.Permission;
import com.business.common.CodeConstant;
import com.business.common.ResponseBean;
import com.business.controller.CommonController;
import com.business.service.system.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: taoye
 * @Description: permission controller
 * @Date: 16:44 2018/8/10
 */
@Controller
public class PermissionController extends CommonController {
    private final static Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Resource
    private PermissionService permissionService;


    /**
     * 根据userid 查询一级菜单
     *
     * @author taoye
     */
    @RequestMapping(value = "/menu/first", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean<List<Permission>> selectMenuListByUserId() {
        ResponseBean<List<Permission>> result = new ResponseBean<>();
        Long loginUserId = super.getLoginUserId();
        try {
            List<Permission> permissions = permissionService.selectMenuListByUserId(loginUserId);
            if (CollectionUtils.isEmpty(permissions)) {
                result.setStatus(CodeConstant.NOT_EXIST);
                return result;
            }
            result.setData(permissions);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 根据userid, pid 查询二级菜单
     *
     * @author taoye
     */
    @RequestMapping(value = "/menu/second/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean<List<Permission>> selectSecondLevelMenuListByUserIdAndPid(@PathVariable("id") Long pid) {
        ResponseBean<List<Permission>> result = new ResponseBean<>();
        Long loginUserId = super.getLoginUserId();
        try {
            List<Permission> permissions = permissionService.selectSecondLevelMenuListByUserIdAndPid(loginUserId, pid);
            if (CollectionUtils.isEmpty(permissions)) {
                result.setStatus(CodeConstant.NOT_EXIST);
                return result;
            }
            result.setData(permissions);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setStatus(CodeConstant.ERROR);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}
