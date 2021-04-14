package com.rbac.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbac.common.constant.BaseConstants;
import com.rbac.common.util.DateUtils;
import com.rbac.common.util.StringUtils;
import com.rbac.common.util.sql.SqlUtil;
import com.rbac.system.domain.SysRole;
import com.rbac.system.domain.SysRoleExample;
import com.rbac.system.domain.SysRoleMenu;
import com.rbac.system.mapper.SysRoleMapper;
import com.rbac.system.service.ISysRoleMenuService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
	@Autowired
	SysRoleMapper roleMapper;
	@Autowired
	ISysRoleMenuService roleMenuService;
	@Autowired
	ISysUserRoleService userRoleService;

	@Override
	@Transactional
	public Integer insertSelective(SysRole role) {
		role.setCreateTime(DateUtils.getNowDate());
		role.setDeleted(BaseConstants.NOT_DELETED);

		Integer result = roleMapper.insertSelective(role);

		updateMenuRelationOfRole(role);

		return result;
	}

	@Override
	@Transactional
	public Integer deleteByPrimaryKey(Long roleId) {
		userRoleService.deleteByRoleId(roleId);
		roleMenuService.deleteByRoleId(roleId);
		return roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	@Transactional
	public Integer deleteByPrimaryKey(List<Long> roleIds) {
		if (StringUtils.isEmpty(roleIds)) {
			return 0;
		}
		int deleteCount = 0;
		for (Long roleId : roleIds) {
			deleteCount += deleteByPrimaryKey(roleId);
		}
		return deleteCount;
	}

	@Override
	@Transactional
	public Integer updateSelective(SysRole role) {
		role.setUpdateTime(DateUtils.getNowDate());

		updateMenuRelationOfRole(role);

		return roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public SysRole selectByPrimaryKey(Long roleId) {

		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public List<SysRole> listByRoleKeyEqualsTo(String roleKey) {
		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria c1 = example.createCriteria();
		c1.andRoleKeyEqualTo(roleKey);
		return roleMapper.selectByExample(example);
	}

	@Override
	public List<SysRole> listByRole(SysRole role) {
		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria c1 = example.createCriteria();
		if (StringUtils.isNotNull(role)) {

			if (StringUtils.isNotNull(role.getRoleKey())) {
				c1.andRoleKeyLike(SqlUtil.getFuzzQueryParam(role.getRoleKey()));
			}
			if (StringUtils.isNotNull(role.getRoleName())) {
				c1.andRoleNameLike(SqlUtil.getFuzzQueryParam(role.getRoleName()));
			}
		}

		return roleMapper.selectByExample(example);
	}

	@Override
	public List<SysRole> listByRoleId(List<Long> roleIds) {
		List<SysRole> roleList = new ArrayList<SysRole>();
		for (Long roleId : new HashSet<Long>(roleIds)) {
			SysRole role = selectByPrimaryKey(roleId);
			if (StringUtils.isNotNull(role)) {

				roleList.add(role);
			}
		}
		return roleList;
	}

	/**
	 * 删除旧的 角色--菜单关系，创建新的 角色--菜单关系
	 * 
	 * @param role
	 * @return
	 */
	private Integer updateMenuRelationOfRole(SysRole role) {
		if (StringUtils.isNull(role) || StringUtils.isNull(role.getId())) {
			return 0;
		}
		// 根据角色ID删除 关联关系
		roleMenuService.deleteByRoleId(role.getId());

		// 创建新的关联关系
		int count = 0;
		for (Long menuId : role.getMenuIds()) {
			SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getId());
			rm.setMenuId(menuId);
			count += roleMenuService.insertSelective(rm);
		}
		return count;
	}

}
