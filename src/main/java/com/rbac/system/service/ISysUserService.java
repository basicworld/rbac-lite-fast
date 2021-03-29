package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysUser;

public interface ISysUserService extends BaseService<SysUser> {
	/**
	 * list users using params<br>
	 * 
	 * if params is null or empty, then return all users
	 * 
	 * @param user
	 * @return
	 */
	List<SysUser> listByUser(SysUser user);

	/**
	 * get user list by userName equals to
	 * 
	 * @param userName
	 * @return
	 */
	List<SysUser> listbyUserNameEqualsTo(String userName);

	/**
	 * check if user is admin
	 * 
	 * @param userId
	 * @return
	 */
	Boolean isAdmin(Long userId);

	/**
	 * check if user is not admin
	 * 
	 * @param userId
	 * @return
	 */
	Boolean isNotAdmin(Long userId);

	/**
	 * update user password by primary key
	 * 
	 * @param user{id, password}
	 * @return
	 */
	Integer updatePasswordByPrimaryKey(SysUser user);

}
