package com.rbac.framework.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rbac.common.util.BaseUtils;
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
     * 使用用户名，从数据库获取用户信息<br>
     * 成功获取用户后，返回LoginUser对象。用户不存在的抛出UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名，从数据库获取SysUser()用户对象
        SysUser user = BaseUtils.firstItemOfList(userService.listbyUserNameEqualsTo(username));
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 获取该用户所有的角色id
        List<Long> roleIds = new ArrayList<Long>();
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(userRoles)) {
            roleIds = userRoles.stream().map(v -> v.getRoleId()).collect(Collectors.toList());
        }
        user.setRoleIds(roleIds);

        // 获取该用户所有的角色代码 即SysRole().roleKey
        Set<String> roleKeys = roleService.listByRoleId(roleIds).stream().map(v -> v.getRoleKey())
                .collect(Collectors.toSet());

        // 获取该用户所有的权限代码 即SysMenu().perms
        Set<String> menuPerms = new HashSet<String>();
        for (SysRoleMenu roleMenu : roleMenuService.listByRoleId(roleIds)) {
            SysMenu menu = menuService.selectByPrimaryKey(roleMenu.getMenuId());
            if (null != menu) {
                menuPerms.add(menu.getPerms());
            }
        }
        // 构造登录用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user); // 登录用户对象
        loginUser.setRoles(roleKeys); // 登录用户的角色关键字集合
        loginUser.setPermissions(menuPerms); // 登录用户的权限关键字集合
        if (logger.isDebugEnabled()) {
            logger.debug("用户{}的所有角色: {}", user.getUserName(), roleKeys);
        }
        return loginUser;
    }

}
