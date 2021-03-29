package com.rbac.system.constant;

import com.rbac.common.constant.BaseConstants;

/**
 * 角色常量
 * 
 * @author wlfei
 *
 */
public class RoleConstants {
	/** 角色已启用 */
	public static final byte STATUS_ENABLE = BaseConstants.STATUS_ENABLE;
	/** 角色已禁用 */
	public static final byte STATUS_DISABLE = BaseConstants.STATUS_DISABLE;

	/**
	 * roleKey of admin<br>
	 * other role should not use this roleKey
	 */
	public static final String ADMIN_ROLE_KEY = "admin";
}
