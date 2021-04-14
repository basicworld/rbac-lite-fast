package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysRoleMenu;

/**
 * 角色--菜单 关联关系service
 * 
 * @author wlfei
 *
 */
public interface ISysRoleMenuService extends BaseService<SysRoleMenu> {
	/**
	 * 删除角色ID关联的 角色--菜单 关系<br>
	 * 只删除关联关系，不删除角色或菜单
	 * 
	 * @param roleId
	 * @return
	 */
	Integer deleteByRoleId(Long roleId);

	/**
	 * 删除菜单ID关联的 角色--菜单 关系<br>
	 * 只删除关联关系，不删除角色或菜单
	 * 
	 * @param roleId
	 * @return
	 */
	Integer deleteByMenuId(Long menuId);

	/**
	 * 根据角色ID获取 角色--菜单 关联关系<br>
	 * 关联关系已去重
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysRoleMenu> listByRoleId(Long roleId);

	/**
	 * 根据角色ID获取 角色--菜单 关联关系<br>
	 * 关联关系已去重
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysRoleMenu> listByRoleId(List<Long> roleIds);
}
