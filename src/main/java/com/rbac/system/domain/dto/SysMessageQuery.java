package com.rbac.system.domain.dto;

/**
 * 消息列表查询对象
 *
 * @author wlfei
 * @date 2021-05-06
 */
public class SysMessageQuery {
    /** 未读置顶标记：true--置顶 false--不置顶 */
    private Boolean unreadTop;

    /** 未读置顶标记：true--置顶 false--不置顶 */
    public Boolean getUnreadTop() {
        return unreadTop;
    }

    public void setUnreadTop(Boolean unreadTop) {
        this.unreadTop = unreadTop;
    }

}
