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
	 * 判断用户密码过期情况<br>
	 * true-过期 false-不过期
	 * 
	 * 
	 * @param userId
	 * @param sendMessage true-过期发送站内信 false-不发送
	 * @return
	 */
	Boolean checkIfPasswordExpired(Long userId, boolean sendMessage);

	/**
	 * 判断用户密码过期情况<br>
	 * true-过期 false-不过期
	 * 
	 * @param user
	 * @param sendMessage
	 */
	Boolean checkIfPasswordExpired(SysUser user, boolean sendMessage);

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

	/**
	 * 更新用户个人信息
	 * 
	 * @param user
	 */
	Integer updatePersonalInfoSelective(SysUser user);

}
