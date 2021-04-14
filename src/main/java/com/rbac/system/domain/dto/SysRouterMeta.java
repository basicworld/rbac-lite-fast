package com.rbac.system.domain.dto;

/**
 * 前端路由的META信息DTO
 * 
 * @author wlfei
 *
 */
public class SysRouterMeta {

    public SysRouterMeta() {
        super();
    }

    public SysRouterMeta(String title, String icon) {
        super();
        this.title = title;
        this.icon = icon;
    }

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/icons/svg
     */
    private String icon;

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "SysRouterMeta [title=" + title + ", icon=" + icon + "]";
    }

}
