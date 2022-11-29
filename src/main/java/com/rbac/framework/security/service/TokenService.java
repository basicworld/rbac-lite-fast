package com.rbac.framework.security.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rbac.common.constant.BaseConstants;
import com.rbac.framework.redis.RedisCache;
import com.rbac.framework.security.domain.LoginUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token工具
 *
 * @author ruoyi
 */
@Component
public class TokenService {
    /** 令牌过期时间（分钟） */
    @Value("${my.token.expire_time}")
    private Integer TOKEN_EXPIRE_TIME;

    /** 令牌密钥 */
    @Value("${my.token.secret}")
    private String TOKEN_SECRET;

    /** 令牌标识符 */
    @Value("${my.token.header}")
    private String TOKEN_HEADER;
    /** 1000毫秒 即一秒钟 */
    protected static final long MILLIS_SECOND = 1000;

    /** 60*1000毫秒 即一分钟 */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    @Autowired
    private RedisCache redisCache;

    /**
     * 从HttpServletRequest获取用户身份信息
     *
     * @return LoginUser 缓存中有用户登录信息时<br>
     *         null 不存在登录信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        return getLoginUser(token);
    }

    /**
     * 用token获取用户身份信息
     *
     * @param token
     * @return 正常情况下返回LoginUser()对象<br>
     *         入参异常或缓存无数据，返回null
     */
    public LoginUser getLoginUser(String token) {
        // 如果入参不为空，则从redis获取用户信息
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(BaseConstants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid); // 构造redis查询键
            // 获取redis存储的用户信息
            LoginUser user = redisCache.getCacheObject(userKey);
            return user;
        }
        // 如果入参为空，则返回null
        return null;
    }

    /**
     * 设置用户身份信息 即更新redis缓存
     */
    public void setLoginUser(LoginUser loginUser) {
        if (null != loginUser && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 从redis缓存删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(BaseConstants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足TOKEN_EXPIRE_TIME分钟，自动刷新缓存
     *
     * @param token 令牌
     * @return void
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE * TOKEN_EXPIRE_TIME) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期<br>
     * 将令牌有效期延长一个TOKEN_EXPIRE_TIME
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + TOKEN_EXPIRE_TIME * MILLIS_MINUTE);
        // 构建redis键
        String userKey = getTokenKey(loginUser.getToken());
        // 写入redis：键=userKey 值=loginUser 过期时间=TOKEN_EXPIRE_TIME（分钟）
        redisCache.setCacheObject(userKey, loginUser, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从请求中获取请求token
     *
     * @param request 请求
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(BaseConstants.TOKEN_PREFIX)) {
            token = token.replace(BaseConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 生成redis缓存用的键
     *
     *
     * @param uuid 唯一ID
     * @return 用于redis登录用户缓存的键
     */
    private String getTokenKey(String uuid) {
        return BaseConstants.LOGIN_TOKEN_KEY + uuid;
    }
}
