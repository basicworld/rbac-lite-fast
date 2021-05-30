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
     * 超级管理员角色代码、角色唯一标识符<br>
     * 具有唯一性，其他角色的代码（标识符）不应与此相同
     */
    public static final String ADMIN_ROLE_KEY = "admin";
}
