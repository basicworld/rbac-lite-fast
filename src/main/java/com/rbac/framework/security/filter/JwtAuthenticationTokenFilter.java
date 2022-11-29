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
 * token过滤器 验证token有效性<br>
 * 对于有效的token，延长token的有效期
 *
 * @author wlfei
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    public static Log logger = LogFactory.getLog(JwtAuthenticationTokenFilter.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 过滤器<br>
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("进入Token过滤器...");
        }
        // 使用请求header中的token，从缓存获取用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 如果存在缓存信息，则进行验证
        if (null != loginUser && null == SecurityUtils.getAuthentication()) {
            if (logger.isDebugEnabled()) {
                logger.debug("开始验证Token...");
            }
            // 验证token 刷新有效期
            tokenService.verifyToken(loginUser);

            /**
             * UsernamePasswordAuthenticationToken继承AbstractAuthenticationToken实现Authentication
             * 所以当在页面中输入用户名和密码之后首先会进入到UsernamePasswordAuthenticationToken验证(Authentication)，
             * 然后生成的Authentication会被交由AuthenticationManager来进行管理
             * 而AuthenticationManager管理一系列的AuthenticationProvider，
             * 而每一个Provider都会通UserDetailsService和UserDetail来返回一个
             * 以UsernamePasswordAuthenticationToken实现的带用户名和密码以及权限的Authentication
             * 
             */
            // https://www.cnblogs.com/softidea/p/6716807.html
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,
                    loginUser.getPassword(), loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
