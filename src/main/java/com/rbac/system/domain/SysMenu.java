package com.rbac.system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单bean
 *
 * @author: wlfei
 * @description: com.rbac.system.domain
 * @date:2021年4月14日
 */
public class SysMenu {
    /** 主键 */
    private Long id;

    /** 父菜单id */
    private Long parentId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单路径 */
    private String path;

    /** 菜单组件 */
    private String component;

    /** 菜单排序 */
    private Integer sort;

    /** 是否为外链 */
    private Byte isFrame;

    /** 是否可见 */
    private Byte visible;

    /** 状态 */
    private Byte status;

    /** 权限标识 */
    private String perms; // 权限字符串，如system, system:user

    /** 图标 */
    private String icon;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 删除标记 */
    private Byte deleted;

    /** 描述 */
    private String note;

    /** 子菜单列表 */
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getIsFrame() {
        return isFrame;
    }

    public void setIsFrame(Byte isFrame) {
        this.isFrame = isFrame;
    }

    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((component == null) ? 0 : component.hashCode());
        result = prime * result + ((createBy == null) ? 0 : createBy.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isFrame == null) ? 0 : isFrame.hashCode());
        result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((perms == null) ? 0 : perms.hashCode());
        result = prime * result + ((sort == null) ? 0 : sort.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((updateBy == null) ? 0 : updateBy.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        result = prime * result + ((visible == null) ? 0 : visible.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SysMenu other = (SysMenu) obj;
        if (component == null) {
            if (other.component != null)
                return false;
        } else if (!component.equals(other.component))
            return false;
        if (createBy == null) {
            if (other.createBy != null)
                return false;
        } else if (!createBy.equals(other.createBy))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (deleted == null) {
            if (other.deleted != null)
                return false;
        } else if (!deleted.equals(other.deleted))
            return false;
        if (icon == null) {
            if (other.icon != null)
                return false;
        } else if (!icon.equals(other.icon))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isFrame == null) {
            if (other.isFrame != null)
                return false;
        } else if (!isFrame.equals(other.isFrame))
            return false;
        if (menuName == null) {
            if (other.menuName != null)
                return false;
        } else if (!menuName.equals(other.menuName))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (parentId == null) {
            if (other.parentId != null)
                return false;
        } else if (!parentId.equals(other.parentId))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (perms == null) {
            if (other.perms != null)
                return false;
        } else if (!perms.equals(other.perms))
            return false;
        if (sort == null) {
            if (other.sort != null)
                return false;
        } else if (!sort.equals(other.sort))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (updateBy == null) {
            if (other.updateBy != null)
                return false;
        } else if (!updateBy.equals(other.updateBy))
            return false;
        if (updateTime == null) {
            if (other.updateTime != null)
                return false;
        } else if (!updateTime.equals(other.updateTime))
            return false;
        if (visible == null) {
            if (other.visible != null)
                return false;
        } else if (!visible.equals(other.visible))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SysMenu [id=" + id + ", parentId=" + parentId + ", menuName=" + menuName + ", path=" + path
                + ", component=" + component + ", sort=" + sort + ", isFrame=" + isFrame + ", visible=" + visible
                + ", status=" + status + ", perms=" + perms + ", icon=" + icon + ", createBy=" + createBy
                + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", deleted="
                + deleted + ", note=" + note + ", children=" + children + "]";
    }

}