package com.rbac.common.constant;

/**
 * restful api结果常量
 * 
 * @author wlfei
 * @date 2021-04-15
 */
public class ResultConstants {
    /**
     * 成功 code
     */
    public static final int CODE_SUCCESS = 20000;

    /**
     * 用户端错误 code
     */
    public static final int CODE_USER_ERROR = 40000;

    /**
     * token错误 code
     */
    public static final int CODE_ILLEGAL_TOKEN = 50008;
    /**
     * token过期 code
     */
    public static final int CODE_TOKEN_EXPIRED = 50014;
    /**
     * 鉴权错误 code
     */
    public static final int CODE_AUTH_FAIL = 50016;

    /**
     * 服务端错误 code
     */
    public static final int CODE_SERVER_ERROR = 50000;
    /**
     * 成功信息 message
     */
    public static final String MESSAGE_SUCCESS = "success";
    /**
     * 用户错误信息 message
     */
    public static final String MESSAGE_USER_ERROR = "user error";
    /**
     * 服务端错误信息 message
     */
    public static final String MESSAGE_SERVER_ERROR = "server error";
}
