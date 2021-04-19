package com.rbac.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbac.common.constant.BaseConstants;
import com.rbac.common.util.DateUtils;
import com.rbac.common.util.sql.SqlUtil;
import com.rbac.framework.web.page.TreeSelect;
import com.rbac.system.constant.MenuConstants;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.domain.SysMenuExample;
import com.rbac.system.domain.dto.SysRouter;
import com.rbac.system.domain.dto.SysRouterMeta;
import com.rbac.system.mapper.SysMenuMapper;
import com.rbac.system.service.ISysMenuService;
import com.rbac.system.service.ISysRoleMenuService;

@Service
public class SysMenuServiceImpl implements ISysMenuService {
	@Autowired
	SysMenuMapper menuMapper;

	@Autowired
	ISysRoleMenuService roleMenuService;

	@Override
	public Integer insertSelective(SysMenu menu) {
		menu.setCreateTime(DateUtils.getNowDate());
		menu.setDeleted(BaseConstants.NOT_DELETED);
		return menuMapper.insertSelective(menu);
	}

	@Override
	public Integer deleteByPrimaryKey(Long menuId) {
		return menuMapper.deleteByPrimaryKey(menuId);
	}

	@Override
	public Integer updateSelective(SysMenu menu) {
		return menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public SysMenu selectByPrimaryKey(Long menuId) {
		return menuMapper.selectByPrimaryKey(menuId);
	}

	@Override
	public List<SysMenu> listByMenu(SysMenu menu) {
		SysMenuExample example = new SysMenuExample();
		SysMenuExample.Criteria c1 = example.createCriteria();
		if (null != menu && StringUtils.isNotEmpty(menu.getMenuName())) {
			c1.andMenuNameLike(SqlUtil.getFuzzQueryParam(menu.getMenuName()));
		}
		return menuMapper.selectByExample(example);
	}

	@Override
	public List<SysMenu> listByRoleId(Long roleId) {
		// 使用 distinct()去重
		return roleMenuService.listByRoleId(roleId).stream().map(v -> selectByPrimaryKey(v.getMenuId())).distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<SysMenu> listByRoleId(List<Long> roleIds) {
		// 使用 distinct()去重
		return roleMenuService.listByRoleId(roleIds).stream().map(v -> selectByPrimaryKey(v.getMenuId())).distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<SysMenu> buildMenuTree(List<SysMenu> menuList) {
		Map<Long, List<SysMenu>> menuByParentIdMap = menuList.stream()
				.collect(Collectors.groupingBy(SysMenu::getParentId));
		menuList.stream().forEach(dept -> dept.setChildren(menuByParentIdMap.get(dept.getId())));
		return menuList.stream().filter(v -> v.getParentId().equals(MenuConstants.ROOT_MENU_ID))
				.collect(Collectors.toList());
	}

	@Override
	public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menuList) {
		List<SysMenu> menuTrees = buildMenuTree(menuList);
		return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	@Override
	public SysRouter menu2Router(SysMenu menu) {
		SysRouter router = new SysRouter();
		router.setComponent(menu.getComponent());
		router.setHidden(MenuConstants.VISIBLE_YES != menu.getVisible());
		router.setMeta(new SysRouterMeta(menu.getMenuName(), menu.getIcon()));
		router.setName(menu.getMenuName());
		router.setPath(menu.getPath());

		if (CollectionUtils.isNotEmpty(menu.getChildren())) {
			router.setRedirect("noRedirect");
			router.setAlwaysShow(true);
			List<SysRouter> children = menu2Router(menu.getChildren());
			router.setChildren(children);
		}

		return router;
	}

	@Override
	public List<SysRouter> menu2Router(List<SysMenu> menuList) {
		List<SysRouter> routerList = new ArrayList<SysRouter>();
		for (SysMenu menu : menuList) {
			routerList.add(menu2Router(menu));
		}
		return routerList;
	}

}
