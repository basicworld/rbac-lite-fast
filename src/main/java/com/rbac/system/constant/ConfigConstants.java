/**
 *
 */
package com.rbac.system.constant;

import com.rbac.common.constant.BaseConstants;

/**
 * 系统配置常量
 *
 * @author wlfei
 * @date 2021-05-08
 */
public class ConfigConstants {
	/** sql 分页从首页开始 */
	public static final int PAGE_START = 1;
	/** sql 分页中单页面最大项目数量 */
	public static final int PAGE_SIZE = 9999;
	/** sql 排序关键字 */
	public static final String SORT_KEY = "sort";

	/** 缓存前缀标识 */
	public static final String CONFIG_CACHE_PREFIX = "system_config:";

	/** spring配置名称 */
	public static final String NAME_OF_DB_PROPERTY_SOURCE = "system_config:";

	/** 配置关键字：用户密码过期时间（天） */
	public static final String KEY_USER_PASSWORD_EXPIRE_TIME = "my.user.password.expire_time";

	/**
	 * spring.mail.host=smtp.qq.com <br>
	 * spring.mail.from=yourqq@qq.com<br>
	 * spring.mail.username=yourqq@qq.com <br>
	 * spring.mail.password=yourpassword<br>
	 * spring.mail.port=466 <br>
	 * spring.mail.properties.mail.smtp.ssl.enable=true
	 */
	/** 配置关键字：开启邮件发送功能 */
	public static final String KEY_MAIL_SMTP_OPEN = "my.system.mail.open";

	/** config_value、optional_values 多个可选项之间的分隔符 */
	public static final String ITEM_SEPARATOR = ",";
	/** optional_values 单个可选项中，可选值与描述文字 之间的分隔符 */
	public static final String VALUE_AND_NOTE_SEPARATOR = ":";

	/** 表单类型：文本输入框 */
	public static final String FORM_TYPE_STRING = "string";
	/** 表单类型：密码输入框 */
	public static final String FORM_TYPE_PASSWORD = "password";
	/** 表单类型：单选框 */
	public static final String FORM_TYPE_RADIO = "radio";
	/** 表单类型：多选框 */
	public static final String FORM_TYPE_CHECKBOX = "checkbox";

	/** 值类型：字符串 不区分大小写 */
	public static final String VALUE_TYPE_STRING = "string";
	/** 值类型：integer 不区分大小写 */
	public static final String VALUE_TYPE_INTEGER = "integer";
	/** 值类型：byte 不区分大小写 */
	public static final String VALUE_TYPE_BYTE = "byte";

	/** 已删除 */
	public static final byte DELETE_YES = BaseConstants.YES;
	/** 未删除 */
	public static final byte DELETE_NO = BaseConstants.NO;

	/** 是多选型的值 */
	public static final byte MULTIPLE_YES = BaseConstants.YES;
	/** 是单项值 */
	public static final byte MULTIPLE_NO = BaseConstants.NO;

	/** 可见 */
	public static final byte VISIBLE_YES = BaseConstants.YES;
	/** 不可见 */
	public static final byte VISIBLE_NO = BaseConstants.NO;

	/** 启用 */
	public static final byte ENABLE_YES = BaseConstants.YES;
	/** 未启用 */
	public static final byte ENABLE_NO = BaseConstants.NO;

	/** 系统内建 */
	public static final byte SYSTEM_BUILT_YES = BaseConstants.YES;
	/** 非系统内建 */
	public static final byte SYSTEM_BUILT_NO = BaseConstants.NO;

}
