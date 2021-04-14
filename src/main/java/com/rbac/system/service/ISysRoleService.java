package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysRole;

public interface ISysRoleService extends BaseService<SysRole> {
    /**
     * 根据角色ID批量删除角色<br>
     * 具有事务控制
     * 
     * @param roleIds
     * @return
     */
    Integer deleteByPrimaryKey(List<Long> roleIds);

    /**
     * 根据角色代码获取角色列表<br>
     * 返回列表中含有一个或0个角色
     * 
     * @param roleKey
     * @return
     */
    List<SysRole> listByRoleKeyEqualsTo(String roleKey);

    /**
     * 根据条件查询角色列表<br>
     * 
     * 如果条件为空，则反馈所有角色
     * 
     * @param role
     * @return
     */
    List<SysRole> listByRole(SysRole role);

    /**
     * 根据角色ID列表，批量查询角色
     * 
     * @param roleIds
     * @return 角色列表
     */
    List<SysRole> listByRoleId(List<Long> roleIds);

}
