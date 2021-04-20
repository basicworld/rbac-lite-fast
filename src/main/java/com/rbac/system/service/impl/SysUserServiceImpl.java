package com.rbac.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbac.common.util.sql.SqlUtil;
import com.rbac.system.constant.RoleConstants;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.SysUserExample;
import com.rbac.system.domain.SysUserRole;
import com.rbac.system.mapper.SysUserMapper;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService {
	@Autowired
	SysUserMapper userMapper;
	@Autowired
	ISysUserRoleService userRoleService;
	@Autowired
	ISysRoleService roleService;

	@Override
	public Integer insertSelective(SysUser user) {
		user.setCreateTime(new Date());
		Integer result = userMapper.insertSelective(user);

		updateRoleRelationForUser(user);

		return result;
	}

	@Override
	@Transactional
	public Integer deleteByPrimaryKey(Long userId) {
		userRoleService.deleteByUserId(userId);
		return userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	@Transactional
	public Integer deleteByPrimaryKey(List<Long> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return 0;
		}

		int count = 0;
		for (Long userId : userIds) {
			count += deleteByPrimaryKey(userId);
		}
		return count;
	}

	@Override
	@Transactional
	public Integer updateSelective(SysUser user) {
		user.setUpdateTime(new Date());

		updateRoleRelationForUser(user);

		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public Integer updatePasswordByPrimaryKey(SysUser user) {
		user.setUpdateTime(new Date());

		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public SysUser selectByPrimaryKey(Long userId) {

		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<SysUser> listByUser(SysUser user) {
		SysUserExample example = new SysUserExample();
		SysUserExample.Criteria c1 = example.createCriteria();

		if (null != user) {
			if (StringUtils.isNotBlank(user.getUserName())) {
				c1.andUserNameLike(SqlUtil.getFuzzQueryParam(user.getUserName()));
			}
			if (StringUtils.isNotBlank(user.getNickName())) {
				c1.andNickNameLike(SqlUtil.getFuzzQueryParam(user.getNickName()));
			}
			if (StringUtils.isNotBlank(user.getEmail())) {
				c1.andEmailLike(SqlUtil.getFuzzQueryParam(user.getEmail()));
			}
			if (StringUtils.isNotBlank(user.getPhone())) {
				c1.andPhoneLike(SqlUtil.getFuzzQueryParam(user.getPhone()));
			}
		}
		return userMapper.selectByExample(example);
	}

	@Override
	public List<SysUser> listbyUserNameEqualsTo(String userName) {
		SysUserExample example = new SysUserExample();
		SysUserExample.Criteria c1 = example.createCriteria();

		c1.andUserNameEqualTo(userName);
		return userMapper.selectByExample(example);
	}

	@Override
	public Boolean isAdmin(Long userId) {
		List<Long> roleIds = userRoleService.listByUserId(userId).stream().map(v -> v.getRoleId())
				.collect(Collectors.toList());
		return roleService.listByRoleId(roleIds).stream()
				.filter(v -> RoleConstants.ADMIN_ROLE_KEY.equals(v.getRoleKey())).collect(Collectors.toList())
				.size() > 0;
	}

	@Override
	public Boolean isNotAdmin(Long userId) {
		return !isAdmin(userId);
	}

	/**
	 * 删除旧的 用户--角色关系，创建新的 用户--角色关系
	 * 
	 * @param user
	 * @return
	 */
	private Integer updateRoleRelationForUser(SysUser user) {
		if (null == user || null == user.getId()) {
			return 0;
		}
		// 根据用户ID删除关系
		userRoleService.deleteByUserId(user.getId());

		// 创建新的关系
		int count = 0;
		if (CollectionUtils.isNotEmpty(user.getRoleIds())) {
			for (Long roleId : user.getRoleIds()) {
				SysUserRole ur = new SysUserRole();
				ur.setUserId(user.getId());
				ur.setRoleId(roleId);
				count += userRoleService.insertSelective(ur);
			}
		}
		return count;
	}

}
