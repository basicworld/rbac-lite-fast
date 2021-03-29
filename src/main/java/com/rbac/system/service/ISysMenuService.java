package com.rbac.system.service;

import java.util.List;

import com.rbac.framework.web.page.TreeSelect;
import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.domain.dto.SysRouter;

public interface ISysMenuService extends BaseService<SysMenu> {
	/**
	 * get menu list
	 * 
	 * @param menu, unnecessary params
	 * @return
	 */
	List<SysMenu> listByMenu(SysMenu menu);

	/**
	 * get menu list which relate with target roleId
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysMenu> listByRoleId(Long roleId);

	/**
	 * get menu list which relate with target roleIds
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysMenu> listByRoleId(List<Long> roleIds);

	/**
	 * change menu_list to menu_tree
	 * 
	 * @param menuList
	 * @return
	 */
	List<SysMenu> buildMenuTree(List<SysMenu> menuList);

	/**
	 * change menu_list to menu_select_tree
	 * 
	 * @param menuList
	 * @return
	 */
	List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menuList);

	/**
	 * change menu to router
	 * 
	 * @param menu
	 * @return
	 */
	SysRouter menu2Router(SysMenu menu);

	/**
	 * change menu_list to router_list
	 * 
	 * @param menuList
	 * @return
	 */
	List<SysRouter> menu2Router(List<SysMenu> menuList);

}
