package com.business.service;

import com.business.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: taoye
 * @Description:
 * @Date: 14:22 2018/8/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MailServiceTest {

    @Resource
    private MailService mailService;

    @Test
    public void sendAttachmentMail() {
        Map<String, Object> map = new HashMap<>();
        map.put("imei", "测试1");
        map.put("endTime", new Date());
        map.put("startTime", new Date());
        map.put("phoneNumber", "18506107646");
        mailService.sendMail("2323882831@qq.com", "测试", "email/Template", map);
    }

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1805023826@qq.com");
        message.setTo("2323882831@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }
}