package com.rbac.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.constant.BaseConstants;
import com.rbac.common.util.BCryptUtils;
import com.rbac.common.util.DateUtils;
import com.rbac.common.util.RSAUtils;
import com.rbac.common.util.ServletUtils;
import com.rbac.common.util.StringUtils;
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
 * user api<br>
 * 
 * api available only for admin_users<br>
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
	 * get user list by page
	 * 
	 * @param userDTO
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@GetMapping("/list")
	public TableDataInfo list(SysUser user) {
		logger.debug("get user list...");
		startPage();
		List<SysUser> userList = userService.listByUser(user);
		if (StringUtils.isNotEmpty(userList)) {
			for (SysUser u : userList) {
				u.setPassword("******");
			}
		}
		return getDataTable(userList);

	}

	/**
	 * add user
	 * 
	 * @param userDTO
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysUser user) {
		logger.debug("add user...");

		// check null: userName and password
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
			return AjaxResult.error("userName and password cannot be null!");
		}
		// check value: duplicate userName
		List<SysUser> usersWithSameUserName = userService.listbyUserNameEqualsTo(user.getUserName());
		if (StringUtils.isNotEmpty(usersWithSameUserName)) {
			return AjaxResult.error(StringUtils.format("duplicate userName({})!", user.getUserName()));
		}
		// check: roleIds cannot be empty or null
		if (StringUtils.isEmpty(user.getRoleIds())) {
			return AjaxResult.error("roleIds cannot be null or empty!");

		}
		// check: invalid roleId
		List<SysRole> selectedRoles = new ArrayList<SysRole>();
		for (Long roleId : user.getRoleIds()) {
			SysRole role = roleService.selectByPrimaryKey(roleId);
			if (StringUtils.isNull(role)) {
				return AjaxResult.error("invalid roleId!");
			}
			selectedRoles.add(role);
		}
		// check: normal_user cannot create admin_user
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (userService.isNotAdmin(loginUser.getUser().getId())) {
			for (SysRole role : selectedRoles) {
				if (RoleConstants.ADMIN_ROLE_KEY.contentEquals(role.getRoleKey())) {
					return AjaxResult.error("non_admin_user cannot create user with admin_role!");
				}
			}
		}

		// decode password using rsa
		String rawPassword = RSAUtils.decrypt(rsaPrivateKey, user.getPassword());
		// password encode using Bcrypt
		String encodePassword = BCryptUtils.encode(rawPassword);
		user.setPassword(encodePassword);

		// set necessary user value
		user.setDeleted(BaseConstants.NOT_DELETED);

		userService.insertSelective(user);

		return AjaxResult.success(StringUtils.format("user(userName={}) created successfully", user.getUserName()));
	}

	/**
	 * get user detail info
	 * 
	 * @param userId
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@GetMapping("/{userId}")
	public AjaxResult getDetail(@PathVariable Long userId) {
		logger.debug("get user detail info...");
		SysUser user = userService.selectByPrimaryKey(userId);

		if (StringUtils.isNotNull(user)) {
			user.setPassword("******");
			// get roleIds of this user
			List<Long> roleIds = userRoleService.listByUserId(userId).stream().map(v -> v.getRoleId())
					.collect(Collectors.toList());
			user.setRoleIds(roleIds);
		}
		return AjaxResult.success(user);
	}

	/**
	 * delete user by user_id
	 * 
	 * @param userIds[userId1, userId2...]
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@DeleteMapping("/{userIds}")
	public AjaxResult delete(@PathVariable Long[] userIds) {
		logger.debug("delete user by id...");

		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		for (Long userId : userIds) {
			if (StringUtils.isNull(userId)) {
				return AjaxResult.error("invalid userId included!");
			}
			// check: you cannot delete yourself!
			if (userId.equals(loginUser.getUser().getId())) {
				return AjaxResult.error("you cannot delete yourself!");
			}
		}
		// check: normal_user cannot delete admin_user
		if (userService.isNotAdmin(loginUser.getUser().getId())) {
			for (Long userId : userIds) {
				if (userId.equals(loginUser.getUser().getId())) {
					return AjaxResult.error("you cannot delete yourself!");
				}
				if (userService.isAdmin(userId)) {
					return AjaxResult.error("non_admin_user cannot delete user with admin role!");
				}

			}
		}
		int deleteCount = 0;
		for (Long userId : userIds) {
			deleteCount += userService.deleteByPrimaryKey(userId);
		}
		return AjaxResult.success(StringUtils.format("{} user(s) deleted successfully", deleteCount));
	}

	/**
	 * update user
	 * 
	 * @param user
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PutMapping
	public AjaxResult update(@Validated @RequestBody SysUser user) {
		logger.debug("update user...");
		// null check
		if (StringUtils.isNull(user.getId())) {
			return AjaxResult.error("user id cannot be null!");
		}
		// not exsit check
		SysUser existUser = userService.selectByPrimaryKey(user.getId());
		if (StringUtils.isNull(existUser)) {
			return AjaxResult.error(StringUtils.format("user(id={}) not exist!", user.getId()));
		}
		// check: cannot update self
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (user.getId().equals(loginUser.getUser().getId())) {
			return AjaxResult.error("you cannot update yourself!");
		}
		// check: normal_user cannot delete admin_user
		if (userService.isNotAdmin(loginUser.getUser().getId()) && userService.isAdmin(user.getId())) {
			return AjaxResult.error("non_admin_user cannot update user with admin role!");
		}
		// prepare update value
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
	 * change user password
	 * 
	 * @param user{id, password}
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:user')")
	@PutMapping("/resetPassword")
	public AjaxResult resetPassword(@RequestBody SysUser user) {
		logger.debug("admin change user password...");
		// null check
		if (StringUtils.isNull(user.getId()) || StringUtils.isEmpty(user.getPassword())) {
			return AjaxResult.error("id and password cannot be null!");
		}
		// user exist check
		SysUser existUser = userService.selectByPrimaryKey(user.getId());
		if (StringUtils.isNull(existUser)) {
			return AjaxResult.error(StringUtils.format("user(id={}) not exist!", user.getId()));
		}
		// check: normal_user cannot reset password for admin_user
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (userService.isNotAdmin(loginUser.getUser().getId()) && userService.isAdmin(user.getId())) {
			return AjaxResult.error("non_admin_user cannot reset password for user with admin role!");
		}
		// decode password using rsa
		String rawPassword = RSAUtils.decrypt(rsaPrivateKey, user.getPassword());
		// password encode using Bcrypt
		String encodePassword = BCryptUtils.encode(rawPassword);

		// prepare value
		SysUser updateUser = new SysUser();
		updateUser.setId(user.getId());
		updateUser.setPassword(encodePassword);
		updateUser.setPwdUpdateTime(DateUtils.getNowDate());

		userService.updatePasswordByPrimaryKey(updateUser);

		return AjaxResult.success();
	}

}
