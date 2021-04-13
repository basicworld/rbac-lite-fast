package com.rbac.framework.security.domain;

/**
 * 接收前台用户登录信息
 * 
 * @author wlfei
 *
 */
public class LoginBody {
	/** 登录用户名 */
	private String username;
	/** 密码 */
	private String password;
	/**
	 * 验证码
	 */
	private String code;

	/**
	 * 验证码唯一标识
	 */
	private String uuid = "";

	public String getCode() {
		return code;
	}

	public String getUuid() {
		return uuid;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginBody [username=" + username + ", password=" + password + ", code=" + code + ", uuid=" + uuid + "]";
	}

}
