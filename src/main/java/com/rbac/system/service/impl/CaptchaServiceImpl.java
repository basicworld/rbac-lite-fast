package com.rbac.system.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import com.google.code.kaptcha.Producer;
import com.rbac.common.constant.BaseConstants;
import com.rbac.framework.redis.RedisCache;
import com.rbac.system.domain.Captcha;
import com.rbac.system.service.ICaptchaService;

/**
 * TODO
 * 
 * @author wlfei
 * @date 2021-04-14
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {
	public static Log logger = LogFactory.getLog(CaptchaServiceImpl.class);
	@Resource(name = "captchaProducer")
	private Producer captchaProducer;

	@Resource(name = "captchaProducerMath")
	private Producer captchaProducerMath;
	@Autowired
	RedisCache redisCache;

	@Override
	public Captcha create() throws IOException {
		// 生成验证码唯一ID
		String uuid = UUID.randomUUID().toString().replace("-", "");

		String capStr = null, code = null;
		BufferedImage image = null;
		// 生成数学验证码
		String capText = captchaProducerMath.createText();
		capStr = capText.substring(0, capText.lastIndexOf("@"));
		code = capText.substring(capText.lastIndexOf("@") + 1);
		image = captchaProducerMath.createImage(capStr);

		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", os);
		} catch (IOException e) {
			throw e;
		}

		String base64 = Base64.encodeBase64String(os.toByteArray());
		Captcha cap = new Captcha();
		cap.setBase64(base64);
		cap.setCode(code);
		cap.setUuid(uuid);

		// 验证码写入redis缓存
		String redisKey = genCaptchaRedisKey(uuid);
		redisCache.setCacheObject(redisKey, code, BaseConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

		return cap;
	}

	@Override
	public Boolean validate(Captcha item) {
		if (null == item || StringUtils.isEmpty(item.getCode()) || StringUtils.isEmpty(item.getUuid())) {
			return false;
		}
		String redisKey = genCaptchaRedisKey(item);
		String code = redisCache.getCacheObject(redisKey);

		boolean isSame = StringUtils.isNotEmpty(code) && code.equals(item.getCode());
		return isSame;
	}

	/**
	 * 生成验证码的redis key
	 * 
	 * @param uuid
	 * @return
	 */
	private String genCaptchaRedisKey(String uuid) {
		return "cap-" + uuid;
	}

	/**
	 * 生成验证码的redis key
	 * 
	 * @param captcha
	 * @return
	 */
	private String genCaptchaRedisKey(Captcha captcha) {
		return genCaptchaRedisKey(captcha.getUuid());
	}

}
