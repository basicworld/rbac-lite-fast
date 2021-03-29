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
 * menu api<br>
 * 
 * only provides query api<br>
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
	 * get menu list
	 * 
	 * @param menu
	 * @return
	 */
	@GetMapping("/list")
	public AjaxResult list(SysMenu menu) {
		logger.debug("get menu list...");
		List<SysMenu> menuList = menuService.listByMenu(menu);
		return AjaxResult.success(menuList);
	}

	/**
	 * get menu tree
	 * 
	 * @param menu
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
	 * get menu_select_tree
	 * 
	 * @param menu
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
