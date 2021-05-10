package com.rbac.common.constant;

/**
 * 全局基本常量
 * 
 * @author wlfei
 * @date 2021-04-15
 */
public class BaseConstants {
    /** 状态：已启用 */
    public static final byte STATUS_ENABLE = 0;
    /** 状态：已禁用 */
    public static final byte STATUS_DISABLE = 1;
    /**
     * 已删除
     */
    public static final byte DELETED = 1;
    /**
     * 未删除
     */
    public static final byte NOT_DELETED = 0;
    /**
     * 是
     */
    public static final byte YES = 1;
    /**
     * 否
     */
    public static final byte NO = 0;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀 redis key
     */
    public static final String LOGIN_USER_KEY = "login_user_key:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期（分钟）
     */
    public static final int CAPTCHA_EXPIRATION = 5;

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /** 字符串 true */
    public static final String STRING_TRUE = "true";

    /** 字符串 false */
    public static final String STRING_FALSE = "false";

}
