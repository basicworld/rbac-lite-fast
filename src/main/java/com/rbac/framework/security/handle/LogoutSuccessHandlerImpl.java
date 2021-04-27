package com.rbac.framework.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.rbac.common.util.ServletUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理<br>
     * 清空缓存，通过response返回成功信息
     * 
     * @return void
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);

        if (null != loginUser) {
            String username = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            if (logger.isInfoEnabled()) {
                logger.info("用户{}已退出，缓存已清空", username);
            }
        }

        // 返回给用户的信息
        String jString = JSON.toJSONString(AjaxResult.success());
        ServletUtils.renderString(response, jString);
    }
}
