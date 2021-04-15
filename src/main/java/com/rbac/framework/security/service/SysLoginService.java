package com.rbac.framework.security.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.rbac.common.util.StringUtils;
import com.rbac.framework.security.domain.LoginUser;

/**
 * 登录验证服务
 * 
 * @author wlfei
 *
 */
@Component
public class SysLoginService {
    public static Log logger = LogFactory.getLog(SysLoginService.class);

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    /**
     * 登录验证，并返回token<br>
     * 验证用户名和密码是否正确，正确则为用户生成token<br>
     * 不正确时抛出异常
     * 
     * @param username
     * @param password
     * @return 成功登录时，返回有效token
     */
    public String login(final String username, String password) {
        logger.debug(StringUtils.format("用户登录验证，用户名：{}，密码：{}", username, password));
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                logger.info(StringUtils.format("用户名或密码错误({}, {})!", username, password));
                throw new BadCredentialsException("用户名或密码错误");
            } else {
                logger.error(e);
                throw e;
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(loginUser);
        logger.info(StringUtils.format("用户{}登陆成功", username));
        return token;
    }

}
