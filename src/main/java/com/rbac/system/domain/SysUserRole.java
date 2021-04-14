package com.rbac.system.domain;

/**
 * 用户--角色 关联bean
 * 
 * @author: wlfei
 * @description: com.rbac.system.domain
 * @date:2021年4月14日
 */
public class SysUserRole {
    private Long id;

    private Long userId;

    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRole [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
    }

}