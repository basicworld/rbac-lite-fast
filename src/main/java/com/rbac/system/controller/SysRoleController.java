package com.rbac.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.constant.BaseConstants;
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
 * 角色controller<br>
 *
 * 仅超级管理员有权限修改<br>
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

    /**
     * 分页获取角色列表<br>
     * 不做鉴权，登录用户都可以获取角色列表
     *
     * @param role
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(SysRole role) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取角色列表...");
        }
        startPage();
        List<SysRole> roleList = roleService.listByRole(role);
        return getDataTable(roleList);
    }

    /**
     * 新增角色<br>
     * 鉴权：只有具备system:role权限的用户可以新增角色
     *
     * @param role {roleKey, roleName}
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:role')")
    @PostMapping("/create")
    public AjaxResult add(@Validated @RequestBody SysRole role) {
        if (logger.isDebugEnabled()) {
            logger.debug("新增角色...");
        }
        // 空值检查
        if (StringUtils.isEmpty(role.getRoleName()) || StringUtils.isEmpty(role.getRoleKey())) {
            return AjaxResult.error("角色名称、角色代码不能为空!");
        }
        // 角色代码重复检查
        List<SysRole> roleListWithSameKey = roleService.listByRoleKeyEqualsTo(role.getRoleKey());
        if (CollectionUtils.isNotEmpty(roleListWithSameKey)) {
            return AjaxResult.error("角色代码重复：{}!", role.getRoleKey());
        }

        // 设置删除标记：未删除
        role.setDeleted(BaseConstants.NOT_DELETED);
        // 设置启用标记：已启用
        role.setStatus(RoleConstants.STATUS_ENABLE);

        roleService.insertSelective(role);
        return AjaxResult.success();
    }

    /**
     * 根据角色主键ID删除角色<br>
     * 鉴权：只有具备system:role权限的用户可以删除角色
     *
     * @param roleIds 角色ID列表[roleId1, roleId2...]
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:role')")
    @PostMapping("/delete/{roleIds}")
    public AjaxResult delete(@PathVariable List<Long> roleIds) {
        if (logger.isDebugEnabled()) {
            logger.debug("删除角色：{}", roleIds);
        }
        if (CollectionUtils.isEmpty(roleIds)) {
            return AjaxResult.error("输入角色主键为空，无需删除!");
        }

        // 检查：不能删除超级管理员角色，该角色有且仅有一个，系统自带，不能删除
        for (Long roleId : roleIds) {
            SysRole role = roleService.selectByPrimaryKey(roleId);
            if (null != role && role.getRoleKey().contentEquals(RoleConstants.ADMIN_ROLE_KEY)) {
                return AjaxResult.error("不能删除超级管理员角色!");
            }
        }

        // 删除计数
        int deleteCount = roleService.deleteByPrimaryKey(roleIds);

        return AjaxResult.success("成功删除{}个角色!", deleteCount);
    }

    /**
     * 根据角色主键获取角色详情<br>
     * 鉴权：只有具备system:role权限的用户可以获取角色详情
     *
     * @param roleId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:role')")
    @GetMapping("/detail/{roleId}")
    public AjaxResult getDetail(@PathVariable Long roleId) {
        // 非空检查
        if (null == roleId) {
            return AjaxResult.error("角色主键不能为空!", roleId);
        }
        // 检查：角色不存在
        SysRole role = roleService.selectByPrimaryKey(roleId);
        if (null == role) {
            return AjaxResult.error("角色(id={})不存在!", roleId);
        }
        // 获取角色的关联菜单列表
        Long[] menuIds = roleMenuService.listByRoleId(roleId).stream().map(v -> v.getMenuId()).toArray(Long[]::new);
        /*
         * 筛选所有叶子节点的菜单 用于前端展示
         *
         * 前端实现的展示效果：所有叶子节点为选中状态，所有叶子节点的父级节点为半选中状态
         */
        List<SysMenu> allMenus = menuService.listByMenu(new SysMenu());
        Set<Long> allMenuParentIds = allMenus.stream().map(v -> v.getParentId()).collect(Collectors.toSet());
        Long[] menuLeafNodeIds = Stream.of(menuIds).filter(v -> !allMenuParentIds.contains(v)).toArray(Long[]::new);

        role.setMenuIds(Arrays.asList(menuLeafNodeIds));

        return AjaxResult.success(role);
    }

    /**
     * 根据主键更新角色<br>
     * 鉴权：只有具备system:role权限的用户可以更新角色<br>
     * 通过入参的menuIds[]做角色与菜单的关联
     *
     * @param role {id, ...}
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:role')")
    @PostMapping("/update")
    public AjaxResult update(@Validated @RequestBody SysRole role) {
        if (logger.isDebugEnabled()) {
            logger.debug("更新角色...");
        }
        // 非空检查
        if (null == role.getId()) {
            return AjaxResult.error("角色主键不能为空!");
        }

        // 检查：不能更新超级管理员角色
        SysRole existRole = roleService.selectByPrimaryKey(role.getId());
        if (null != existRole && existRole.getRoleKey().contentEquals(RoleConstants.ADMIN_ROLE_KEY)) {
            return AjaxResult.error("不能更新超级管理员角色!");
        }
        // 准备角色更新信息
        SysRole updateRole = new SysRole();
        updateRole.setId(role.getId());
        updateRole.setRoleName(role.getRoleName());
        updateRole.setRoleKey(role.getRoleKey());
        updateRole.setSort(role.getSort());
        updateRole.setDataScope(role.getDataScope());
        updateRole.setNote(role.getNote());
        updateRole.setMenuIds(role.getMenuIds()); // 关联菜单

        roleService.updateSelective(updateRole);

        return AjaxResult.success();
    }
}
