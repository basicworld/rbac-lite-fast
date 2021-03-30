package com.rbac.system.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.util.StringUtils;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.system.domain.Captcha;
import com.rbac.system.service.ICaptchaService;

/**
 * 验证码controller
 * 
 * @author wlfei
 *
 */
@RestController
public class CaptchaController {
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	ICaptchaService captchaService;

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	@GetMapping("/captcha")
	public AjaxResult getCaptcha() {
		try {
			Captcha cap = captchaService.create();
			logger.debug(StringUtils.format("captcha uuid={}, code={}", cap.getUuid(), cap.getCode()));
			cap.setCode("");
			return AjaxResult.success(cap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AjaxResult.error("生成验证码异常");
		}

	}
}
