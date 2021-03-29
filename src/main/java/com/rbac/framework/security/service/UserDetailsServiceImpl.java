package com.rbac.framework.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rbac.common.util.BaseUtils;
import com.rbac.common.util.StringUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.domain.SysRoleMenu;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.SysUserRole;
import com.rbac.system.service.ISysMenuService;
import com.rbac.system.service.ISysRoleMenuService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

/**
 * 实现spring security 的UserDetailsService接口<br>
 * 根据用户名获取用户对象
 * 
 * @author wlfei
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	public static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	ISysUserService userService;
	@Autowired
	ISysRoleService roleService;
	@Autowired
	ISysUserRoleService userRoleService;
	@Autowired
	ISysRoleMenuService roleMenuService;
	@Autowired
	ISysMenuService menuService;

	/**
	 * 成功获取用户后，返回LoginUser对象
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		SysUser user = BaseUtils.firstItemOfList(userService.listbyUserNameEqualsTo(username));
		if (null == user) {
			throw new UsernameNotFoundException("用户不存在");
		}
		// 所有角色id
		List<Long> roleIds = new ArrayList<Long>();
		List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
		if (StringUtils.isNotEmpty(userRoles)) {
			roleIds = userRoles.stream().map(v -> v.getRoleId()).collect(Collectors.toList());
		}
		user.setRoleIds(roleIds);

		// 所有角色代码
		Set<String> roleKeys = roleService.listByRoleId(roleIds).stream().map(v -> v.getRoleKey())
				.collect(Collectors.toSet());
		// 所有权限代码
		Set<String> menuPerms = new HashSet<String>();
		for (SysRoleMenu rm : roleMenuService.listByRoleId(roleIds)) {
			SysMenu menu = menuService.selectByPrimaryKey(rm.getMenuId());
			if (StringUtils.isNotNull(menu)) {
				menuPerms.add(menu.getPerms());
			}
		}

		LoginUser loginUser = new LoginUser();
		loginUser.setUser(user);
		loginUser.setRoles(roleKeys);
		loginUser.setPermissions(menuPerms);
		return loginUser;
	}

}
