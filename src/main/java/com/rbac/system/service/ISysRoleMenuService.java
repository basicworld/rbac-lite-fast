package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysRoleMenu;

public interface ISysRoleMenuService extends BaseService<SysRoleMenu> {
	/**
	 * delete role_menu_relation by roleId equals to
	 * 
	 * @param roleId
	 * @return
	 */
	Integer deleteByRoleId(Long roleId);

	/**
	 * delete role_menu_relation by menuId equals to
	 * 
	 * @param menuId
	 * @return
	 */
	Integer deleteByMenuId(Long menuId);

	/**
	 * list role_menu_relation by roleId equals to
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysRoleMenu> listByRoleId(Long roleId);

	/**
	 * list role_menu_relation by roleIds equals to
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysRoleMenu> listByRoleId(List<Long> roleIds);
}
