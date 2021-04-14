package com.rbac.system.domain.dto;

import java.util.List;

/**
 * 前端路由DTO
 * 
 * @author wlfei
 *
 */
public class SysRouter {
    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private SysRouterMeta meta;

    /**
     * 子路由
     */
    private List<SysRouter> children;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getRedirect() {
        return redirect;
    }

    public String getComponent() {
        return component;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public SysRouterMeta getMeta() {
        return meta;
    }

    public List<SysRouter> getChildren() {
        return children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public void setMeta(SysRouterMeta meta) {
        this.meta = meta;
    }

    public void setChildren(List<SysRouter> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "SysRouter [name=" + name + ", path=" + path + ", hidden=" + hidden + ", redirect=" + redirect
                + ", component=" + component + ", alwaysShow=" + alwaysShow + ", meta=" + meta + ", children="
                + children + "]";
    }

}
