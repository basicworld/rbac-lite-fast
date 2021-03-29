package com.rbac.system.domain.dto;

/**
 * mapping password value
 * 
 * @author wlfei
 *
 */
public class UserPwdDTO {
	// old password
	private String password;

	// new password
	private String newPassword;

	public String getPassword() {
		return password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
