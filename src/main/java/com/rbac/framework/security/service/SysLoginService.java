package com.rbac.framework.security.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.rbac.framework.security.domain.LoginUser;

/**
 * 登录验证服务
 *
 * @author wlfei
 *
 */
@Component
public class SysLoginService {
    private static final Logger logger = LoggerFactory.getLogger(SysLoginService.class);

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
     * @exception DisabledException       用户已禁用<br>
     * @exception LockedException         用户已锁定<br>
     * @exception BadCredentialsException 用户名或密码错误<br>
     * @exception Exception               其他错误<br>
     *
     */
    public String login(final String username, String password) {
        if (logger.isDebugEnabled()) {
            logger.debug("用户登录验证，用户名：{}，密码：{}", username, password);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()
            // loadUserByUsername()通过用户名获取数据库的用户信息（包括密码、角色、权限），这个步骤不会验证密码
            // 然后交给authenticationManager去验证密码
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof DisabledException) {
                // 抛出用户禁用异常
                throw new DisabledException("用户已禁用");
            } else if (e instanceof LockedException) {
                // 抛出用户锁定异常
                throw new LockedException("用户已锁定");
            } else if (e instanceof BadCredentialsException) {
                // 抛出用户名或密码错误异常
                throw new BadCredentialsException("用户名或密码错误");
            } else {
                // 其他错误
                logger.error(e.getMessage());
                throw e;
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(loginUser);
        if (logger.isInfoEnabled()) {
            logger.info("用户{}登陆成功", username);
        }
        return token;
    }

}
