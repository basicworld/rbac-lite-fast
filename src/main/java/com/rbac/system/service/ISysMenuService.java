package com.rbac.system.service;

import java.util.List;

import com.rbac.framework.web.page.TreeSelect;
import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.domain.dto.SysRouter;

/**
 * 菜单service
 * 
 * @author wlfei
 *
 */
public interface ISysMenuService extends BaseService<SysMenu> {
	/**
	 * 获取菜单列表
	 * 
	 * @param menu 查询参数支持：{menuName,}
	 * @return
	 */
	List<SysMenu> listByMenu(SysMenu menu);

	/**
	 * 查询角色ID关联的菜单列表
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysMenu> listByRoleId(Long roleId);

	/**
	 * 查询角色ID关联的菜单列表
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysMenu> listByRoleId(List<Long> roleIds);

	/**
	 * 用菜单列表构造菜单树
	 * 
	 * @param menuList
	 * @return
	 */
	List<SysMenu> buildMenuTree(List<SysMenu> menuList);

	/**
	 * 用菜单列表构造菜单下拉选择树
	 * 
	 * @param menuList
	 * @return
	 */
	List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menuList);

	/**
	 * 菜单转为路由
	 * 
	 * @param menu
	 * @return
	 */
	SysRouter menu2Router(SysMenu menu);

	/**
	 * 菜单列表转为路由列表
	 * 
	 * @param menuList
	 * @return
	 */
	List<SysRouter> menu2Router(List<SysMenu> menuList);

}
