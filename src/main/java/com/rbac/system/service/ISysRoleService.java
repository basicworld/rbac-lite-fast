package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysRole;

public interface ISysRoleService extends BaseService<SysRole> {
	/**
	 * get role list whose roleKey equals to target roleKey
	 * 
	 * @param roleKey
	 * @return
	 */
	List<SysRole> listByRoleKeyEqualsTo(String roleKey);

	/**
	 * get role list with params<br>
	 * 
	 * if params is null or empty, then return all role
	 * 
	 * @param role
	 * @return
	 */
	List<SysRole> listByRole(SysRole role);

	/**
	 * get role list whose role_id is in roleIds
	 * 
	 * @param roleIds
	 * @return SysRole[], no null value
	 */
	List<SysRole> listByRoleId(List<Long> roleIds);

}
