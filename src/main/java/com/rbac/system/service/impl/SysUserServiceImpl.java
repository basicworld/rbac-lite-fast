package com.rbac.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbac.common.util.DateUtils;
import com.rbac.common.util.StringUtils;
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
		user.setCreateTime(DateUtils.getNowDate());
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
	public Integer updateSelective(SysUser user) {
		user.setUpdateTime(DateUtils.getNowDate());

		updateRoleRelationForUser(user);

		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public Integer updatePasswordByPrimaryKey(SysUser user) {
		user.setUpdateTime(DateUtils.getNowDate());

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

		if (StringUtils.isNotNull(user)) {
			if (StringUtils.isNotEmpty(user.getUserName())) {
				c1.andUserNameLike(SqlUtil.getFuzzQueryParam(user.getUserName()));
			}
			if (StringUtils.isNotEmpty(user.getNickName())) {
				c1.andNickNameLike(SqlUtil.getFuzzQueryParam(user.getNickName()));
			}
			if (StringUtils.isNotEmpty(user.getEmail())) {
				c1.andEmailLike(SqlUtil.getFuzzQueryParam(user.getEmail()));
			}
			if (StringUtils.isNotEmpty(user.getPhone())) {
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
	 * delete old user_role_relation, create new user_role_relation for user
	 * 
	 * @param user
	 * @return
	 */
	private Integer updateRoleRelationForUser(SysUser user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getId())) {
			return 0;
		}
		// update relate roleIds
		userRoleService.deleteByUserId(user.getId());
		int count = 0;
		if (StringUtils.isNotEmpty(user.getRoleIds())) {
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
