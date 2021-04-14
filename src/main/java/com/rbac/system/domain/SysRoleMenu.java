package com.rbac.system.domain;

/**
 * 角色--菜单 关联bean
 * 
 * @author: wlfei
 * @description: com.rbac.system.domain
 * @date:2021年4月14日
 */
public class SysRoleMenu {
    private Long id;

    private Long roleId;

    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "SysRoleMenu [id=" + id + ", roleId=" + roleId + ", menuId=" + menuId + "]";
    }

}