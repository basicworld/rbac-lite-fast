/**
 * 
 */
package com.rbac.system.constant;

import com.rbac.common.constant.BaseConstants;

/**
 * 系统消息常量类
 * 
 * @author wlfei
 * @date 2021-05-05
 */
public class MessageConstants {
	/** 消息对用户可见 */
	public static final byte VISIBLE_YES = BaseConstants.YES;
	/** 消息对用户不可见 */
	public static final byte VISIBLE_NO = BaseConstants.NO;

	/** 消息已读 */
	public static final byte READ_DOWN = BaseConstants.YES;
	/** 消息未读 */
	public static final byte UNREAD = BaseConstants.NO;

	/** 消息已删除 */
	public static final byte DELETE_YES = BaseConstants.YES;
	/** 消息未删除 */
	public static final byte DELETE_NO = BaseConstants.NO;

	/** 消息模版关键字：管理员修改密码 */
	public static final String MODEL_KEY_ADMIN_CHANGE_PASSWORD = "system:admin:changePassword";
	/** 消息模版关键字：管理员停用用户 */
	public static final String MODEL_KEY_ADMIN_DISABLE_USER = "system:admin:disableUser";
	/** 消息模版关键字：管理员启用用户 */
	public static final String MODEL_KEY_ADMIN_ENABLE_USER = "system:admin:enableUser";
	/** 消息模版关键字：管理员修改用户权限 */
	public static final String MODEL_KEY_ADMIN_CHANGE_USER_PERMISSION = "system:admin:changeUserPermission";

	/** 消息模版关键字：用户密码过期提醒 */
	public static final String MODEL_KEY_PERSONAL_PASSWORD_EXPIRE_WARN = "system:personal:passwordExpireWarning";
	/** 消息模版关键字：用户更新信息通知 */
	public static final String MODEL_KEY_PERSONAL_UPDATE_INFO = "system:personal:updateMyInfo";
	/** 消息模版关键字：用户修改密码通知 */
	public static final String MODEL_KEY_PERSONAL_UPDATE_PASSWORD = "system:personal:updatePassword";

	/**
	 * 全部消息主键标记 <br>
	 * 列表中如果包含这个主键，表示要处理用户的所有消息
	 */
	public static final Long ALL_MESSAGE_TAG = -1L;

	public static final String COLUMN_CREATE_TIME = "create_time";

	public static final String COLUMN_HAS_READ = "has_read";
}
