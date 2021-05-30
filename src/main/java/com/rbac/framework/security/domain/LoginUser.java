package com.rbac.framework.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rbac.system.constant.UserConstants;
import com.rbac.system.domain.SysUser;

/**
 * 登录用户身份权限<br>
 * 登录成功后将作为用户的缓存
 *
 * @author wlfei
 *
 */
public class LoginUser implements UserDetails {
    public static final Logger logger = LoggerFactory.getLogger(LoginUser.class);

    private static final long serialVersionUID = 1L;
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 权限标识集合，即SysMenu().perms的集合
     */
    private Set<String> permissions;
    /**
     * 角色标识集合，即SysRole().roleKey的集合
     */
    private Set<String> roles;
    /**
     * 用户对象
     */
    private SysUser user;

    /**
     * 获取角色标识的集合
     *
     * @return SysRole().roleKey的集合
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * 设置角色标识
     *
     * @param roles SysRole().roleKey的集合
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * 获取当前用户所具有的角色
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 该方法用来获取当前用户所具有的角色
        // 参考 https://blog.csdn.net/u012702547/article/details/79019510
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (CollectionUtils.isEmpty(this.roles)) {
            return authorities;
        }
        for (String role : this.roles) {
            if (StringUtils.isBlank(role)) {
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;

    }

    /**
     * 获取密码
     */
    @JsonIgnore
    @Override
    public String getPassword() {

        return user.getPassword();
    }

    /**
     * 获取用户名
     */
    @Override
    public String getUsername() {

        return user.getUserName();
    }

    /**
     * 判断账号是否过期。本系统不设置账号过期策略，全部返回true-不过期
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * 判断账号是否被锁定。本系统不设置账号锁定策略，全部返回true-未锁定
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    /**
     * 判断密码是否过期。本系统不设置密码过期策略，全部返回true-不过期
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * 判断账号是否停用。通过user.status判断。
     *
     * @return true-账号启用<br>
     *         false-账号停用
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return UserConstants.STATUS_ENABLE == user.getStatus();
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * 获取登陆时间
     *
     * @return
     */
    public Long getLoginTime() {
        return loginTime;
    }

    /**
     * 获取登录过期时间
     *
     * @return
     */
    public Long getExpireTime() {
        return expireTime;
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    public SysUser getUser() {
        return user;
    }

    /**
     * 设置token
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 设置登录时间
     *
     * @param loginTime
     */
    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 设置过期时间
     *
     * @param expireTime
     */
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 设置用户对象
     *
     * @param user
     */
    public void setUser(SysUser user) {
        this.user = user;
    }

    /**
     * 获取权限标识集合
     *
     * @return SysMenu().perms的集合
     */
    public Set<String> getPermissions() {
        return permissions;
    }

    /**
     * 设置权限标识集合，即 menu.perms的集合
     *
     * @param permissions SysMenu().perms的集合
     */
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

}
