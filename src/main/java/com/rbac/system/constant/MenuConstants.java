package com.rbac.system.constant;

import com.rbac.common.constant.BaseConstants;

/**
 * 菜单常量
 *
 * @author wlfei
 *
 */
public class MenuConstants {
    /** 菜单可见 */
    public static final byte VISIBLE_YES = BaseConstants.YES;
    /** 菜单不可见 */
    public static final byte VISIBLE_NO = BaseConstants.NO;
    /**
     * 根目录ID<br>
     * 只能有一个根目录 该目录对前端不可见
     */
    public static final Long ROOT_MENU_ID = 0L;
}
