package com.rbac.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt 非对称加密工具类<br>
 * 提供静态方法：加密字符串，判断明文、密文是否一致
 * 
 * @author wlfei
 *
 */
public class BCryptUtils {
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	/**
	 * 使用BCrypt算法加密字符串（密码）
	 * 
	 * @param rawPassword
	 * @return
	 */
	public static String encode(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	/**
	 * 判断两个密码是否相同
	 * 
	 * @param rawPassword     未经加密的密码
	 * @param encodedPassword 经过加密的密码
	 * @return
	 */
	public static Boolean isSamePassword(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}
}
