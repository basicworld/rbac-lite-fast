package com.rbac.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具
 * 
 * @author wlfei
 *
 */
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 生成盐（6位随机乱码）
	 * 
	 * @return String
	 */
	public static String genSalt() {
		final String model = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuffer salt = new StringBuffer();
		char[] m = model.toCharArray();
		for (int i = 0; i < 6; i++) {
			char c = m[(int) (Math.random() * 36)];
			salt = salt.append(c);
		}
		return salt.toString();
	}
}
