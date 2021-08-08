package com.rbac.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.framework.web.page.TreeSelect;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.service.ISysMenuService;

/**
 * 菜单controller<br>
 *
 * 仅提供菜单查询功能，不提供增删改功能<br>
 *
 * @author wlfei
 *
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    @Autowired
    ISysMenuService menuService;

    /**
     * 获取菜单列表<br>
     *
     *
     * @param menu{menuName, } 筛选条件
     * @return 所有符合条件的菜单的列表，入参为空时返回所有菜单
     */
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取菜单列表...");
        }
        List<SysMenu> menuList = getMenuList(menu);
        return AjaxResult.success(menuList);
    }

    /**
     * 获取菜单树
     *
     * @param menu{menuName, } 筛选条件
     * @return 所有符合条件的菜单的树，入参为空时返回所有菜单
     */
    @GetMapping("/tree")
    public AjaxResult treeList(SysMenu menu) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取菜单树...");
        }
        // menu list
        List<SysMenu> menuList = getMenuList(menu);
        // menu list to menu tree
        List<SysMenu> treeList = menuService.buildMenuTree(menuList);
        return AjaxResult.success(treeList);
    }

    /**
     * 获取菜单下拉选择树<br>
     * 用于前端的菜单下拉选择功能，例如基于菜单的授权会用到
     *
     * @param menu{menuName, } 筛选条件
     * @return 所有符合条件的菜单的下拉选择树，入参为空时返回所有菜单的下拉选择树
     */
    @GetMapping("/treeselect")
    public AjaxResult treeSelect(SysMenu menu) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取菜单下拉选择树...");
        }
        // menu list
        List<SysMenu> menuList = getMenuList(menu);
        // menu select tree
        List<TreeSelect> treeSelect = menuService.buildMenuTreeSelect(menuList);
        return AjaxResult.success(treeSelect);
    }

    private List<SysMenu> getMenuList(SysMenu menu) {
        return menuService.listByMenu(menu);
    }

}
