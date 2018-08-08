package com.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: taoye
 * @Description: demo
 * @Date: 17:38 2018/8/7
 */
@Controller

public class HelloController {

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello world";
    }
}
