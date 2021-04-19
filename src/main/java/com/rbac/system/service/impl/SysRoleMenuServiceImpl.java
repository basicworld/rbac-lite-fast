package com.rbac.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbac.system.domain.SysRoleMenu;
import com.rbac.system.domain.SysRoleMenuExample;
import com.rbac.system.mapper.SysRoleMenuMapper;
import com.rbac.system.service.ISysRoleMenuService;

@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {
	@Autowired
	SysRoleMenuMapper roleMenuMapper;

	@Override
	public Integer insertSelective(SysRoleMenu item) {
		return roleMenuMapper.insertSelective(item);
	}

	@Override
	public Integer deleteByPrimaryKey(Long id) {

		return roleMenuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer updateSelective(SysRoleMenu item) {

		return roleMenuMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public SysRoleMenu selectByPrimaryKey(Long id) {

		return roleMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteByRoleId(Long roleId) {
		Integer count = 0;
		for (SysRoleMenu item : listByRoleId(roleId)) {
			count += deleteByPrimaryKey(item.getId());
		}
		return count;
	}

	@Override
	public Integer deleteByMenuId(Long menuId) {
		SysRoleMenuExample example = new SysRoleMenuExample();
		example.createCriteria().andMenuIdEqualTo(menuId);
		Integer count = 0;
		for (SysRoleMenu item : roleMenuMapper.selectByExample(example)) {
			count += deleteByPrimaryKey(item.getId());
		}
		return count;
	}

	@Override
	public List<SysRoleMenu> listByRoleId(Long roleId) {
		SysRoleMenuExample example = new SysRoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		return roleMenuMapper.selectByExample(example);
	}

	@Override
	public List<SysRoleMenu> listByRoleId(List<Long> roleIds) {
		List<SysRoleMenu> roleMenuList = new ArrayList<SysRoleMenu>();
		for (Long roleId : roleIds) {
			List<SysRoleMenu> subList = listByRoleId(roleId);
			roleMenuList.addAll(subList);
		}
		return roleMenuList.stream().filter(v -> (null != v)).collect(Collectors.toList());
	}

}
