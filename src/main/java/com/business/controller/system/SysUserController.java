package com.business.controller.system;

import com.business.common.CodeConstant;
import com.business.common.MessageConstant;
import com.business.common.ResponseBean;
import com.business.controller.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: taoye
 * @Description: 系统用户controller
 * @Date: 16:41 2018/8/10
 */
@Controller
public class SysUserController extends CommonController {
    private final static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        logger.info("login session id:{},ip:{}，{}", request.getSession().getId(), request.getRemoteAddr(), request.getSession().getMaxInactiveInterval());
        return "login";
    }

    public ResponseBean<String> getUserName() {

        ResponseBean<String> bean = new ResponseBean<>();
        bean.setStatus(1);
        bean.setMessage("测试");

        return bean;
    }

    @RequestMapping("/loginSuccess")
    @ResponseBody
    public ResponseBean<String> loginSuccess() {

        ResponseBean<String> bean = getUserName();
        //bean.setMessage("success");

        return bean;
    }


    @RequestMapping("/loginFailure")
//    @ResponseBody
    public String loginFailure(Model model) {
//        ResponseBean response = new ResponseBean<>();
//        response.setStatus(CodeConstant.ERROR);
//        response.setMessage(MessageConstant.LOGIN_FAILURE);
//        return response;
        model.addAttribute("error", MessageConstant.LOGIN_FAILURE);
        return "login";
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
