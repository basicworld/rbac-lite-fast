package com.rbac.framework.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rbac.common.util.SecurityUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.TokenService;

/**
 * token过滤器 验证token有效性 并延长有效token的有效期
 * 
 * @author wlfei
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    public static Log logger = LogFactory.getLog(JwtAuthenticationTokenFilter.class);

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("JWT doFilterInternal...");
        }
        // 从缓存获取用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);

        if (null != loginUser && null == SecurityUtils.getAuthentication()) {
            if (logger.isDebugEnabled()) {
                logger.debug("JWT doFilterInternal verifyToken...");
            }

            tokenService.verifyToken(loginUser);

            /// https://www.cnblogs.com/softidea/p/6716807.html
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,
                    loginUser.getPassword(), loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
