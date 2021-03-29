package com.rbac.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.util.ServletUtils;
import com.rbac.common.util.StringUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.dto.SysRouter;
import com.rbac.system.service.ISysMenuService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

/**
 * router api
 * 
 * @author wlfei
 *
 */
@RestController
@RequestMapping()
public class SysRouterController {

	private static final Logger logger = LoggerFactory.getLogger(SysRouterController.class);

	@Autowired
	ISysMenuService menuService;
	@Autowired
	TokenService tokenService;
	@Autowired
	ISysRoleService roleService;
	@Autowired
	ISysUserRoleService userRoleService;
	@Autowired
	ISysUserService userService;

	/**
	 * show server is alive
	 * 
	 * @return
	 */
	@GetMapping("/ping")
	public AjaxResult pong() {
		return AjaxResult.success("pong");
	}

	/**
	 * get router tree based on user_permission
	 * 
	 * @return
	 */
	@GetMapping("/router")
	public AjaxResult getRouters() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		logger.debug(StringUtils.format("user(id={}) get router...", user.getId()));

		List<SysMenu> menuList = new ArrayList<SysMenu>();
		if (userService.isAdmin(user.getId())) {
			// admin_user get menu
			logger.debug("admin_user get all menus");
			menuList = menuService.listByMenu(new SysMenu());
		} else {
			// normal user get menu
			logger.debug("nomal user get menus with permission");
			Set<Long> roleIds = userRoleService.listByUserId(user.getId()).stream().map(v -> v.getRoleId())
					.collect(Collectors.toSet());
			menuList = menuService.listByRoleId(new ArrayList<Long>(roleIds));
		}
		// build tree
		List<SysMenu> treeList = menuService.buildMenuTree(menuList);
		// build router
		List<SysRouter> routerList = menuService.menu2Router(treeList);

		return AjaxResult.success(routerList);
	}

}
