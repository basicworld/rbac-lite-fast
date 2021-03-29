package com.rbac.framework.security.domain;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rbac.system.constant.UserConstants;
import com.rbac.system.domain.SysUser;

/**
 * 登录用户身份权限<br>
 * 登录成功后将作为用户的缓存
 * 
 * @author wlfei
 *
 */
public class LoginUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户唯一标识
	 */
	private String token;

	/**
	 * 登陆时间
	 */
	private Long loginTime;

	/**
	 * 过期时间
	 */
	private Long expireTime;

	/**
	 * 权限列表 menu.perms列表
	 */
	private Set<String> permissions;
	/**
	 * roleKey列表
	 */
	private Set<String> roles;
	/**
	 * 用户信息
	 */
	private SysUser user;

	/**
	 * @return set of roleKey
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * set of roleKey
	 * 
	 * @param roles
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return null;
	}

	@JsonIgnore
	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUserName();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return UserConstants.STATUS_ENABLE == user.getStatus();
	}

	public String getToken() {
		return token;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public SysUser getUser() {
		return user;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	/**
	 * 
	 * @return set of menu.perms()
	 */
	public Set<String> getPermissions() {
		return permissions;
	}

	/**
	 * set of menu.perms()
	 * 
	 * @param permissions
	 */
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

}
