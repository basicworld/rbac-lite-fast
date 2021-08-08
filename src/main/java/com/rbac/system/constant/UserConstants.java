package com.rbac.system.constant;

import com.rbac.common.constant.BaseConstants;

/**
 * 用户常量
 *
 * @author wlfei
 *
 */
public class UserConstants {
    /** 账号已启用 */
    public static final byte STATUS_ENABLE = BaseConstants.STATUS_ENABLE;
    /** 账号已禁用 */
    public static final byte STATUS_DISABLE = BaseConstants.STATUS_DISABLE;

    /** 昵称最大长度 应小于该长度 */
    public static final int NICKNAME_MAX_LEN = 21;
    /** 昵称最小长度 应大于等于该长度 */
    public static final int NICKNAME_MIN_LEN = 2;
}
