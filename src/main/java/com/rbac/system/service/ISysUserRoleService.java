package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysUserRole;

/**
 * 用户--角色 关联关系service
 *
 * @author wlfei
 * @date 2021-04-14
 */
public interface ISysUserRoleService extends BaseService<SysUserRole> {
    /**
     * 根据用户ID删除 用户--角色 关联关系
     *
     * @param userId 用户ID
     * @return
     */
    Integer deleteByUserId(Long userId);

    /**
     * 根据角色ID删除 用户--角色 关联关系
     *
     * @param roleId 角色ID
     * @return
     */
    Integer deleteByRoleId(Long roleId);

    /**
     * 根据用户ID获取 用户--角色 关联关系
     *
     * @param userId 用户ID
     * @return
     */
    List<SysUserRole> listByUserId(Long userId);
}
