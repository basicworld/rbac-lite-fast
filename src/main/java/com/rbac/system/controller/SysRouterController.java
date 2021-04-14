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
 * 路由API<br>
 * 
 * 只开放查询功能
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
     * 确认后端服务可用性<br>
     * 本接口不做权限检查，已登录、未登录都可以访问
     * 
     * @return
     */
    @GetMapping("/ping")
    public AjaxResult pong() {
        return AjaxResult.success("pong");
    }

    /**
     * 根据用户权限，返回路由树
     * 
     * @return
     */
    @GetMapping("/router")
    public AjaxResult getRouters() {
        // 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        logger.debug(StringUtils.format("用户(id={})请求获取路由树...", user.getId()));

        List<SysMenu> menuList = new ArrayList<SysMenu>();
        if (userService.isAdmin(user.getId())) {
            // 超级管理员
            logger.debug("超级管理员具有完整路由树权限");
            menuList = menuService.listByMenu(new SysMenu());
        } else {
            // 非超级管理员，按权限筛选路由
            logger.debug("非超级管理员根据权限筛选路由");
            Set<Long> roleIds = userRoleService.listByUserId(user.getId()).stream().map(v -> v.getRoleId())
                    .collect(Collectors.toSet());
            menuList = menuService.listByRoleId(new ArrayList<Long>(roleIds));
        }
        // 构造菜单树
        List<SysMenu> treeList = menuService.buildMenuTree(menuList);
        // 构造路由树
        List<SysRouter> routerList = menuService.menu2Router(treeList);

        return AjaxResult.success(routerList);
    }

}