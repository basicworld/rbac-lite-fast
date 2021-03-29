package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysUserRole;

public interface ISysUserRoleService extends BaseService<SysUserRole> {
	/**
	 * delete user_role_relation by userId equals to
	 * 
	 * @param userId
	 * @return
	 */
	Integer deleteByUserId(Long userId);

	/**
	 * delete user_role_relation by roleId equals to
	 * 
	 * @param roleId
	 * @return
	 */
	Integer deleteByRoleId(Long roleId);

	/**
	 * list user_role_relation by userId equals to
	 * 
	 * @param userId
	 * @return
	 */
	List<SysUserRole> listByUserId(Long userId);
}
