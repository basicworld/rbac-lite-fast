package com.rbac.common.constant;

/**
 * 全局基本常量
 * 
 * @author wlfei
 * @date 2021-04-15
 */
public class BaseConstants {
    /** 状态：已启用(byte 0) */
    public static final byte STATUS_ENABLE = 0;
    /** 状态：已禁用(byte 1) */
    public static final byte STATUS_DISABLE = 1;
    /**
     * 已删除(byte 1)
     */
    public static final byte DELETED = 1;
    /**
     * 未删除(byte 0)
     */
    public static final byte NOT_DELETED = 0;
    /**
     * 是(byte 1)
     */
    public static final byte YES = 1;
    /**
     * 否(byte 0)
     */
    public static final byte NO = 0;

    /**
     * 令牌(String "token")
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀(String "Bearer ")
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀 redis key(String "login_user_key:")
     */
    public static final String LOGIN_USER_KEY = "login_user_key:";

    /**
     * 登录用户 redis key(String "login_tokens:")
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期(分钟)(int 5)
     */
    public static final int CAPTCHA_EXPIRATION = 5;

    /** 字符串 true */
    public static final String STRING_TRUE = "true";

    /** 字符串 false */
    public static final String STRING_FALSE = "false";

}
