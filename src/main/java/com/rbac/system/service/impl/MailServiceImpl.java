/**
 *
 */
package com.rbac.system.service.impl;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.rbac.common.constant.BaseConstants;
import com.rbac.system.constant.ConfigConstants;
import com.rbac.system.service.IMailService;
import com.rbac.system.service.ISysConfigService;

/**
 * IMailService实现类
 *
 * @author wlfei
 * @date 2021-05-10
 */
@Service
public class MailServiceImpl implements IMailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private ISysConfigService configService;

    @Override
    public Boolean canSendMail() {
        byte smtpEnable = configService.valueOfConfig(ConfigConstants.KEY_MAIL_SMTP_OPEN, BaseConstants.NO);
        return (BaseConstants.YES == smtpEnable);
    }

    @Override
    public Boolean reloadMailConfigFromCache() {
        String host = configService.valueOfConfig(ConfigConstants.KEY_MAIL_HOST, "localhsot");
        int port = configService.valueOfConfig(ConfigConstants.KEY_MAIL_PORT, 25);
        String username = configService.valueOfConfig(ConfigConstants.KEY_MAIL_USERNAME, "");
        String password = configService.valueOfConfig(ConfigConstants.KEY_MAIL_PASSWORD, "");
        byte useSSL = configService.valueOfConfig(ConfigConstants.KEY_MAIL_SSL_ENABLE, BaseConstants.NO);

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", BaseConstants.STRING_TRUE);
        if (BaseConstants.YES == useSSL) {
            p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }

        mailSender.setJavaMailProperties(p);

        String msg = MessageFormat.format("刷新邮件参数，host：{0}，port：{1}，username：{2}，password：{3}，enable ssl：{4}。", host,
                port, username, password, useSSL);
        logger.warn(msg);

        return true;
    }

    @Override
    public Boolean sendSimpleMail(String to, String subject, String content) {
        if (!canSendMail()) {
            logger.warn("邮箱功能未启用，不能发送邮件：{}", subject);
            return false;
        }

        // 创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件发送人
        String from = configService.valueOfConfig(ConfigConstants.KEY_MAIL_FROM, "");
        String username = configService.valueOfConfig(ConfigConstants.KEY_MAIL_USERNAME, "");
        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(username)) {
            String msg = MessageFormat.format("邮件发送失败缺少必要参数：{0}或{1}", ConfigConstants.KEY_MAIL_FROM,
                    ConfigConstants.KEY_MAIL_USERNAME);
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        message.setFrom(from);
        // 邮件接收人
        message.setTo(to);
        // 邮件主题
        message.setSubject(subject);
        // 邮件内容
        message.setText(content);

        mailSender.send(message);

        return true;
    }

}
