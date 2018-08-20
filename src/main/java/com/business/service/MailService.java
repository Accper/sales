package com.business.service;

import com.business.common.CodeConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @Author: taoye
 * @Description: 发送邮件service
 * @Date: 14:08 2018/8/20
 */
@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    @Resource
    private JavaMailSender sender;
    @Resource
    private TemplateEngine thymeleaf;
    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 发送html模板邮件
     *
     * @param to        待发送的人
     * @param subject   邮件的主题
     * @param modelPath 模板路径
     * @param map       模板参数
     * @return
     * @author taoye
     */
    public int sendMail(String to, String subject, String modelPath, Map<String, Object> map) {
        MimeMessage message;
        try {
            message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            Context context = new Context(new Locale(""), map);
            String emailtext = thymeleaf.process(modelPath, context);
            helper.setText(emailtext, true);
            sender.send(message);
            logger.info("发送成功！");
            return CodeConstant.SUCCESS;
        } catch (Exception e) {
            logger.error("发送html邮件时发生异常！", e);
            e.printStackTrace();
            return CodeConstant.ERROR;
        }
    }
}

