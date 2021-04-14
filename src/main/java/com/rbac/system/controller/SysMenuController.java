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
 * 菜单 api<br>
 * 
 * 仅提供查询功能，不提供增删改<br>
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
     * 获取菜单列表
     * 
     * @param menu 筛选条件
     * @return
     */
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu) {
        logger.debug("get menu list...");
        List<SysMenu> menuList = menuService.listByMenu(menu);
        return AjaxResult.success(menuList);
    }

    /**
     * 获取菜单树
     * 
     * @param menu 筛选条件
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult treeList(SysMenu menu) {
        logger.debug("get menu tree...");
        // menu list
        List<SysMenu> menuList = menuService.listByMenu(menu);
        // menu list to menu tree
        List<SysMenu> treeList = menuService.buildMenuTree(menuList);
        return AjaxResult.success(treeList);
    }

    /**
     * 获取菜单下拉选择数
     * 
     * @param menu 筛选条件
     * @return
     */
    @GetMapping("/treeselect")
    public AjaxResult treeSelect(SysMenu menu) {
        logger.debug("get menu_select_tree...");
        // menu list
        List<SysMenu> menuList = menuService.listByMenu(menu);
        // menu select tree
        List<TreeSelect> treeSelect = menuService.buildMenuTreeSelect(menuList);
        return AjaxResult.success(treeSelect);
    }

}
