package com.rbac.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.constant.BaseConstants;
import com.rbac.common.util.BCryptUtils;
import com.rbac.common.util.RSAUtils;
import com.rbac.common.util.ServletUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.framework.web.page.TableDataInfo;
import com.rbac.system.base.BaseController;
import com.rbac.system.constant.RoleConstants;
import com.rbac.system.domain.SysRole;
import com.rbac.system.domain.SysUser;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

/**
 * 用户API<br>
 * 
 * 开放给具有超级管理员权限的用户<br>
 * 
 * @author wlfei
 *
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);
	@Autowired
	ISysUserService userService;

	@Autowired
	ISysRoleService roleService;

	@Autowired
	ISysUserRoleService userRoleService;

	@Autowired
	TokenService tokenService;

	@Value("${rsa.privateKey}")
	private String rsaPrivateKey;

	/**
	 * 获取用户列表
	 * 
	 * @param userDTO
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@GetMapping("/list")
	public TableDataInfo list(SysUser user) {
		if (logger.isDebugEnabled()) {
			logger.debug("获取用户列表...");
		}
		startPage();
		List<SysUser> userList = userService.listByUser(user);
		if (CollectionUtils.isNotEmpty(userList)) {
			for (SysUser u : userList) {
				// 隐藏密码
				u.setPassword("******");
			}
		}
		return getDataTable(userList);

	}

	/**
	 * 新增用户
	 * 
	 * @param userDTO
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PostMapping("/create")
	public AjaxResult add(@Validated @RequestBody SysUser user) {
		if (logger.isDebugEnabled()) {
			logger.debug("新增用户:{}", user);
		}

		// 空值检查
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
			return AjaxResult.error("用户名或密码不能为空!");
		}
		// 用户名重复检查
		List<SysUser> usersWithSameUserName = userService.listbyUserNameEqualsTo(user.getUserName());
		if (CollectionUtils.isNotEmpty(usersWithSameUserName)) {
			return AjaxResult.error("用户名重复：{}!", user.getUserName());
		}
		// 关联角色ID不能为空
		if (CollectionUtils.isEmpty(user.getRoleIds())) {
			return AjaxResult.error("用户关联角色不能为空!");

		}
		// 角色ID异常检查
		List<SysRole> selectedRoles = new ArrayList<SysRole>();
		for (Long roleId : user.getRoleIds()) {
			SysRole role = roleService.selectByPrimaryKey(roleId);
			if (null == role) {
				return AjaxResult.error("非法角色关联!");
			}
			selectedRoles.add(role);
		}
		// 检查：非管理员用户不能创建管理员用户
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (userService.isNotAdmin(loginUser.getUser().getId())) {
			for (SysRole role : selectedRoles) {
				if (RoleConstants.ADMIN_ROLE_KEY.contentEquals(role.getRoleKey())) {
					return AjaxResult.error("非管理员用户不能创建管理员用户!");
				}
			}
		}

		// RSA密码解密
		String rawPassword = RSAUtils.decrypt(rsaPrivateKey, user.getPassword());
		// 密码加密 Bcrypt
		String encodePassword = BCryptUtils.encode(rawPassword);
		user.setPassword(encodePassword);

		// 设置删除标记：未删除
		user.setDeleted(BaseConstants.NOT_DELETED);

		userService.insertSelective(user);

		return AjaxResult.success("用户{}创建成功", user.getUserName());
	}

	/**
	 * 获取用户信息详情
	 * 
	 * @param userId 用户主键
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@GetMapping("/detail/{userId}")
	public AjaxResult getDetail(@PathVariable Long userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("获取用户详情:{}", userId);
		}
		SysUser user = userService.selectByPrimaryKey(userId);

		if (null != user) {
			// 隐藏密码
			user.setPassword("******");
			// 获取用户关联角色ID
			List<Long> roleIds = userRoleService.listByUserId(userId).stream().map(v -> v.getRoleId())
					.collect(Collectors.toList());
			user.setRoleIds(roleIds);
		}
		return AjaxResult.success(user);
	}

	/**
	 * 删除用户
	 * 
	 * @param userIds 角色ID列表[userId1, userId2...]
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PostMapping("/delete/{userIds}")
	public AjaxResult delete(@PathVariable List<Long> userIds) {
		if (logger.isDebugEnabled()) {
			logger.debug("删除用户:{}", userIds);
		}

		if (CollectionUtils.isEmpty(userIds)) {
			return AjaxResult.error("用户主键列表为空，无需删除!");
		}

		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		for (Long userId : userIds) {
			if (null == userId) {
				return AjaxResult.error("用户主键为空，无需删除!");
			}
			// 检查：用户不能删除自己
			if (userId.equals(loginUser.getUser().getId())) {
				return AjaxResult.error("无法删除用户自己的账号!");
			}
		}
		// 检查：非管理员用户不能删除超级管理员权限的用户
		if (userService.isNotAdmin(loginUser.getUser().getId())) {
			for (Long userId : userIds) {
				if (userService.isAdmin(userId)) {
					return AjaxResult.error("非管理员用户不能删除超级管理员权限的用户!");
				}
			}
		}
		// 执行删除
		int deleteCount = userService.deleteByPrimaryKey(userIds);

		return AjaxResult.success("成功删除{}个账号", deleteCount);
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PostMapping("/update")
	public AjaxResult update(@Validated @RequestBody SysUser user) {
		if (logger.isDebugEnabled()) {
			logger.debug("更新用户:{}", user);
		}
		// 非空检查
		if (null == user.getId()) {
			return AjaxResult.error("用户主键不能为空!");
		}
		// 检查：用户不存在
		SysUser existUser = userService.selectByPrimaryKey(user.getId());
		if (null == existUser) {
			return AjaxResult.error("待更新用户(id={})不存在!", user.getId());
		}
		// 检查：不能更新用户自己的账号
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (user.getId().equals(loginUser.getUser().getId())) {
			return AjaxResult.error("不能更新自己的账号!");
		}
		// 检查：非管理员用户不能更新管理员用户
		if (userService.isNotAdmin(loginUser.getUser().getId()) && userService.isAdmin(user.getId())) {
			return AjaxResult.error("非管理员用户不能更新管理员用户!");
		}
		// 字段准备
		SysUser updateUser = new SysUser();
		updateUser.setId(user.getId());
		updateUser.setUserName(user.getUserName());
		updateUser.setNickName(user.getNickName());
		updateUser.setDeptName(user.getDeptName());
		updateUser.setPhone(user.getPhone());
		updateUser.setEmail(user.getEmail());
		updateUser.setRoleIds(user.getRoleIds());
		updateUser.setStatus(user.getStatus());

		userService.updateSelective(updateUser);

		return AjaxResult.success();
	}

	/**
	 * 修改用户密码
	 * 
	 * @param user 用户信息{id, password}
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PostMapping("/password/reset")
	public AjaxResult resetPassword(@RequestBody SysUser user) {
		if (logger.isDebugEnabled()) {
			logger.debug("修改用户密码:{}", user);
		}
		// 非空检查
		if (null == user.getId() || StringUtils.isEmpty(user.getPassword())) {
			return AjaxResult.error("用户主键和密码不能为空!");
		}
		// 检查：用户不存在
		SysUser existUser = userService.selectByPrimaryKey(user.getId());
		if (null == existUser) {
			return AjaxResult.error("待修改密码用户主键不存在:", user.getId());
		}
		// 检查：非管理员用户不能修改超级管理员密码
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (userService.isNotAdmin(loginUser.getUser().getId()) && userService.isAdmin(user.getId())) {
			return AjaxResult.error("非管理员用户不能修改超级管理员密码!");
		}
		// RSA密码解密
		String rawPassword = RSAUtils.decrypt(rsaPrivateKey, user.getPassword());
		// 密码加密 Bcrypt
		String encodePassword = BCryptUtils.encode(rawPassword);

		// 准备更新内容
		SysUser updateUser = new SysUser();
		updateUser.setId(user.getId());
		updateUser.setPassword(encodePassword);
		updateUser.setPwdUpdateTime(new Date());

		userService.updatePasswordByPrimaryKey(updateUser);

		return AjaxResult.success();
	}

}
