package com.rbac.system.service;

import java.io.IOException;

import com.rbac.system.domain.Captcha;

/**
 * 验证码service
 *
 * @author wlfei
 *
 */
public interface ICaptchaService {
    /**
     * 创建验证码<br>
     * 并通过redis缓存验证码的正确结果
     *
     * @return 一个新验证码
     * @throws IOException
     */
    Captcha create() throws IOException;

    /**
     * 验证验证码是否正确<br>
     * 入参必要参数：uuid和待验证结果code<br>
     * <br>
     * 原理：从通过uuid从redis缓存获取正确的结果，与待验证结果做比较
     *
     * @param item {uuid:xxx, code:xxx}
     * @return true--正确 false--不正确
     */
    Boolean validate(Captcha item);

}
