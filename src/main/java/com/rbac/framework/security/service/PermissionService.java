package com.rbac.framework.security.service;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rbac.common.util.ServletUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.system.constant.RoleConstants;

/**
 * RuoYi首创 自定义权限实现，ss取自SpringSecurity首字母<br>
 * 
 * 使用方法：<br>
 * 在指定的controller上添加注解<br>
 * @PreAuthorize("@ss.hasPermi('system:user')")<br>
 * @PreAuthorize("@ss.hasAnyPermi('system:user,system:dept')")<br>
 * @PreAuthorize("@ss.hasRole('admin')")<br>
 * @PreAuthorize("@ss.hasAnyRoles('role1,role2')")<br>
 * 
 * @author ruoyi
 */
@Service("ss")
public class PermissionService {
	public static Log logger = LogFactory.getLog(PermissionService.class);
	/** 所有权限标识 */
	private static final String ALL_PERMISSION = "*:*";

	/** 管理员角色权限标识 */
	private static final String SUPER_ADMIN = RoleConstants.ADMIN_ROLE_KEY;

	/** 角色字符串分隔符 */
	private static final String ROLE_DELIMETER = ",";

	/** 权限（菜单）字符串分隔符 */
	private static final String PERMISSION_DELIMETER = ",";

	@Autowired
	private TokenService tokenService;

	/**
	 * 验证用户是否具备某权限<br>
	 * 
	 * 超级管理员具备所有权限<br>
	 * 用户权限列表为空则不具备任何权限<br>
	 * 用户权限列表不为空则检验是否具有给定的权限
	 * 
	 * @param permission 权限字符串 即menu.perms
	 * @return true, false
	 */
	public boolean hasPermi(String permission) {
		if (StringUtils.isEmpty(permission)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (null == loginUser) {
			return false;
		}
		// 超级管理员具有所有权限
		if (isUserAdmin(loginUser)) {
			return true;
		}
		// 用户权限列表为空，视为无次权限
		if (CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		return hasPermissions(loginUser.getPermissions(), permission);
	}

	/**
	 * 验证用户是否不具备某权限，与 hasPermi逻辑相反
	 *
	 * @param permission 权限字符串
	 * @return 用户是否不具备某权限
	 */
	public boolean lacksPermi(String permission) {
		return hasPermi(permission) != true;
	}

	/**
	 * 验证用户是否具有以下任意一个权限<br>
	 * 
	 * 超级管理员具备所有权限<br>
	 * 用户权限列表为空则不具备任何权限<br>
	 * 用户权限列表不为空则检验是否具有给定权限列表中的任意一个权限
	 * 
	 * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
	 * @return 用户是否具有以下任意一个权限
	 */
	public boolean hasAnyPermi(String permissions) {
		if (StringUtils.isEmpty(permissions)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (null == loginUser) {
			return false;
		}
		// 超级管理员具有所有权限
		if (isUserAdmin(loginUser)) {
			return true;
		}
		if (CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		// 用户权限集合包含 指定权限列表 中的任意一个权限，视为true
		Set<String> authorities = loginUser.getPermissions();
		for (String permission : permissions.split(PERMISSION_DELIMETER)) {
			if (permission != null && hasPermissions(authorities, permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断用户是否拥有某个角色<br>
	 * 
	 * 超级管理员具备所有角色<br>
	 * 用户角色列表为空则不具备任何角色<br>
	 * 用户角色列表不为空则检验是否具有给定的角色
	 * 
	 * @param role 角色字符串 即roleKey
	 * @return 用户是否具备某角色
	 */
	public boolean hasRole(String role) {
		if (StringUtils.isEmpty(role)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (null == loginUser) {
			return false;
		}
		// 超级管理员具有所有权限
		if (isUserAdmin(loginUser)) {
			return true;
		}
		if (CollectionUtils.isEmpty(loginUser.getRoles())) {
			return false;
		}
		for (String roleKey : loginUser.getRoles()) {
			if (roleKey.equals(StringUtils.trim(role))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证用户是否不具备某角色，与 hassRole逻辑相反。
	 *
	 * @param role 角色名称
	 * @return 用户是否不具备某角色
	 */
	public boolean lacksRole(String role) {
		return hasRole(role) != true;
	}

	/**
	 * 验证用户是否具有以下任意一个角色<br>
	 * 
	 * 超级管理员具备所有角色<br>
	 * 用户角色列表为空则不具备任何角色<br>
	 * 用户角色列表不为空则检验是否具有给定角色列表中的任意一个角色
	 *
	 * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
	 * @return 用户是否具有以下任意一个角色
	 */
	public boolean hasAnyRoles(String roles) {
		if (StringUtils.isEmpty(roles)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (null == loginUser) {
			return false;
		}
		// 超级管理员具有所有权限
		if (isUserAdmin(loginUser)) {
			return true;
		}
		if (CollectionUtils.isEmpty(loginUser.getRoles())) {
			return false;
		}
		for (String role : roles.split(ROLE_DELIMETER)) {
			if (hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否包含权限<br>
	 * 包含所有权限标识符，或包含给定权限，视为true
	 * 
	 * @param permissions 所有权限列表
	 * @param permission  给定权限
	 * @return true--给定权限在权限列表中，或权限列表中包含<所有权限>标识符<br>
	 *         false--不在
	 */
	private boolean hasPermissions(Set<String> permissions, String permission) {
		return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
	}

	/**
	 * 
	 * 判断用户是否为超级管理员<br>
	 * 
	 * @param loginUser
	 * @return true--给定用户的角色中包含<超级管理员角色代码标识><br>
	 *         false-其他
	 */
	private boolean isUserAdmin(LoginUser loginUser) {
		if (CollectionUtils.isEmpty(loginUser.getRoles())) {
			return false;
		}
		for (String roleKey : loginUser.getRoles()) {
			if (SUPER_ADMIN.contains(roleKey)) {
				return true;
			}
		}

		return false;
	}

}
