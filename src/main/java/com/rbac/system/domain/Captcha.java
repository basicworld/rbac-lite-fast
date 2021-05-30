package com.rbac.system.domain;

/**
 * 验证码bean
 *
 * @author wlfei
 *
 */
public class Captcha {
    /**
     * 原始信息 验证码答案
     */
    private String code;
    /**
     * base64编码的图片信息
     */
    private String base64;
    /**
     * 验证码唯一ID
     */
    private String uuid;

    /**
     * 原始信息 验证码结果
     *
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 图片base64编码
     *
     */
    public String getBase64() {
        return base64;
    }

    /**
     * 唯一ID
     *
     */
    public String getUuid() {
        return uuid;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Captcha [code=" + code + ", base64=" + base64 + ", uuid=" + uuid + "]";
    }

}
