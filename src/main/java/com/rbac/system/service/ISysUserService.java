package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysUser;

/**
 * @Description 用户service
 * @author wlfei
 * @date 2021-04-14
 */
public interface ISysUserService extends BaseService<SysUser> {
	/**
	 * 按条件筛选用户列表<br>
	 * 
	 * 如果条件为空，则返回所有用户
	 * 
	 * @param user
	 * @return
	 */
	List<SysUser> listByUser(SysUser user);

	/**
	 * 按用户名等值查询用户列表
	 * 
	 * @param userName
	 * @return
	 */
	List<SysUser> listbyUserNameEqualsTo(String userName);

	/**
	 * 检验用户ID是否 是超级管理员
	 * 
	 * @param userId
	 * @return
	 */
	Boolean isAdmin(Long userId);

	/**
	 * 检验用户ID是否 不是超级管理员
	 * 
	 * @param userId
	 * @return
	 */
	Boolean isNotAdmin(Long userId);

	/**
	 * 根据用户ID更新密码
	 * 
	 * @param user{id, password}
	 * @return
	 */
	Integer updatePasswordByPrimaryKey(SysUser user);

	/**
	 * 根据用户ID批量删除用户
	 * 
	 * @param userIds 用户ID列表
	 * @return
	 */
	Integer deleteByPrimaryKey(List<Long> userIds);

}
