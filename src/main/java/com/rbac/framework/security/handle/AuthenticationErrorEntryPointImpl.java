package com.rbac.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rbac.common.constant.ResultConstants;
import com.rbac.common.util.ServletUtils;
import com.rbac.framework.web.domain.AjaxResult;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author ruoyi
 */
@Component
public class AuthenticationErrorEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        // 返回认证失败
        int code = ResultConstants.CODE_AUTH_FAIL;
        String msg = MessageFormat.format("认证失败，无法访问 {0}", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}
