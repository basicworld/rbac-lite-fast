package com.rbac.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.rbac.common.util.StringUtils;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.framework.web.page.TableDataInfo;
import com.rbac.system.base.BaseController;
import com.rbac.system.constant.RoleConstants;
import com.rbac.system.domain.SysMenu;
import com.rbac.system.domain.SysRole;
import com.rbac.system.service.ISysMenuService;
import com.rbac.system.service.ISysRoleMenuService;
import com.rbac.system.service.ISysRoleService;

/**
 * role api<br>
 * 
 * only available for admin_user<br>
 * 
 * @author wlfei
 *
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired
	ISysRoleService roleService;

	@Autowired
	ISysRoleMenuService roleMenuService;

	@Autowired
	ISysMenuService menuService;

	@GetMapping("/list")
	public TableDataInfo list(SysRole role) {
		logger.debug("list role...");
		startPage();
		List<SysRole> roleList = roleService.listByRole(role);
		return getDataTable(roleList);
	}

	/**
	 * add role
	 * 
	 * @param role{roleKey, roleName}
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:role')")
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysRole role) {
		logger.debug("add role...");
		// check null
		if (StringUtils.isEmpty(role.getRoleName()) || StringUtils.isEmpty(role.getRoleKey())) {
			return AjaxResult.error("roleName and roleKey cannot be null!");
		}
		// duplicate roleKey check
		List<SysRole> roleListWithSameKey = roleService.listByRoleKeyEqualsTo(role.getRoleKey());
		if (StringUtils.isNotEmpty(roleListWithSameKey)) {
			return AjaxResult.error(StringUtils.format("duplicate roleKey({})!", role.getRoleKey()));
		}

		// set necessary value
		role.setDeleted(BaseConstants.NOT_DELETED);
		role.setStatus(RoleConstants.STATUS_ENABLE);

		roleService.insertSelective(role);
		return AjaxResult.success();
	}

	/**
	 * delete role by role.id
	 * 
	 * @param roleIds[roleId1, roleId2...]
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:role')")
	@DeleteMapping("/{roleIds}")
	public AjaxResult delete(@PathVariable Long[] roleIds) {
		if (StringUtils.isEmpty(roleIds)) {
			return AjaxResult.error("zero roleId selected!");
		}

		// check: cannot delete admin_role
		for (Long roleId : roleIds) {
			SysRole role = roleService.selectByPrimaryKey(roleId);
			if (StringUtils.isNotNull(role) && role.getRoleKey().contentEquals(RoleConstants.ADMIN_ROLE_KEY)) {
				return AjaxResult.error("cannot delete admin_role!");
			}
		}

		//
		int deleteCount = 0;
		for (Long roleId : roleIds) {
			deleteCount += roleService.deleteByPrimaryKey(roleId);
		}
		return AjaxResult.success(StringUtils.format("{} role(s) deleted successfully!", deleteCount));
	}

	/**
	 * get detail info of role
	 * 
	 * @param roleId
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:role')")
	@GetMapping("/{roleId}")
	public AjaxResult getDetail(@PathVariable Long roleId) {
		// value check
		if (StringUtils.isNull(roleId)) {
			return AjaxResult.error(StringUtils.format("role id cannot be null!", roleId));
		}

		SysRole role = roleService.selectByPrimaryKey(roleId);
		if (StringUtils.isNull(role)) {
			return AjaxResult.error(StringUtils.format("role(id={}) does not exist!", roleId));
		}
		// get menuIds of this role
		Long[] menuIds = roleMenuService.listByRoleId(roleId).stream().map(v -> v.getMenuId()).toArray(Long[]::new);
		// filter leaf node menu_id of this role
		List<SysMenu> allMenus = menuService.listByMenu(new SysMenu());
		Set<Long> allMenuParentIds = allMenus.stream().map(v -> v.getParentId()).collect(Collectors.toSet());
		Long[] menuLeafNodeIds = Stream.of(menuIds).filter(v -> !allMenuParentIds.contains(v)).toArray(Long[]::new);

		role.setMenuIds(Arrays.asList(menuLeafNodeIds));

		return AjaxResult.success(role);
	}

	/**
	 * update role by role_id
	 * 
	 * @param role{id, ...}
	 * @return
	 */
	@PreAuthorize("@ss.hasPermi('system:role')")
	@PutMapping
	public AjaxResult update(@Validated @RequestBody SysRole role) {
		logger.debug("update role...");
		if (StringUtils.isNull(role.getId())) {
			return AjaxResult.error("role id cannot be null!");
		}

		// cannot update admin role
		SysRole existRole = roleService.selectByPrimaryKey(role.getId());
		if (StringUtils.isNotNull(existRole) && existRole.getRoleKey().contentEquals(RoleConstants.ADMIN_ROLE_KEY)) {
			return AjaxResult.error("cannot update admin_role!");
		}
		// prepare update value
		SysRole updateRole = new SysRole();
		updateRole.setId(role.getId());
		updateRole.setRoleName(role.getRoleName());
		updateRole.setRoleKey(role.getRoleKey());
		updateRole.setSort(role.getSort());
		updateRole.setDataScope(role.getDataScope());
		updateRole.setNote(role.getNote());
		updateRole.setMenuIds(role.getMenuIds());

		roleService.updateSelective(updateRole);

		return AjaxResult.success();
	}
}
