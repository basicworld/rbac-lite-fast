/**
 *
 */
package com.rbac.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public Boolean canSendMail() {
		byte smtpEnable = configService.valueOfConfig(ConfigConstants.KEY_MAIL_SMTP_OPEN, BaseConstants.NO);
		return (BaseConstants.YES == smtpEnable);
	}

	@Override
	public Boolean sendSimpleMail(String to, String subject, String content) {
		if (!canSendMail()) {
			logger.warn("邮箱功能未启用，不能发送邮件 {} 给 {}", subject, to);
			return false;
		}

		// 创建SimpleMailMessage对象
		SimpleMailMessage message = new SimpleMailMessage();

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
