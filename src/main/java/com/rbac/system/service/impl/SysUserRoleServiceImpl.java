package com.rbac.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbac.system.domain.SysUserRole;
import com.rbac.system.domain.SysUserRoleExample;
import com.rbac.system.mapper.SysUserRoleMapper;
import com.rbac.system.service.ISysUserRoleService;

@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {
	@Autowired
	SysUserRoleMapper userRoleMapper;

	@Override
	public Integer insertSelective(SysUserRole item) {
		return userRoleMapper.insertSelective(item);
	}

	@Override
	public Integer deleteByPrimaryKey(Long id) {

		return userRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer updateSelective(SysUserRole item) {

		return userRoleMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public SysUserRole selectByPrimaryKey(Long id) {

		return userRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteByUserId(Long userId) {
		Integer count = 0;
		for (SysUserRole item : listByUserId(userId)) {
			count += deleteByPrimaryKey(item.getId());
		}
		return count;
	}

	@Override
	public Integer deleteByRoleId(Long roleId) {
		SysUserRoleExample example = new SysUserRoleExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		Integer count = 0;
		for (SysUserRole item : userRoleMapper.selectByExample(example)) {
			count += deleteByPrimaryKey(item.getId());
		}
		return count;
	}

	@Override
	public List<SysUserRole> listByUserId(Long userId) {
		SysUserRoleExample example = new SysUserRoleExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return userRoleMapper.selectByExample(example);
	}

}
