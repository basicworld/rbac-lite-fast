package com.rbac.system.controller;

import java.text.MessageFormat;
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
import com.rbac.system.constant.UserConstants;
import com.rbac.system.domain.SysRole;
import com.rbac.system.domain.SysUser;
import com.rbac.system.service.ISysMessageService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

/**
 * 用户controller<br>
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

    @Autowired
    ISysMessageService msgService;

    /**
     * 分页获取用户列表<br>
     * 鉴权：拥有system:user权限的用户可以访问
     *
     * @param sysUser
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
        // 隐藏密码，不对前端展示真实的密码
        if (CollectionUtils.isNotEmpty(userList)) {
            for (SysUser userItem : userList) {
                userItem.setPassword("******");
            }
        }
        return getDataTable(userList);

    }

    /**
     * 新增用户<br>
     * 鉴权：拥有system:user权限的用户可以访问<br>
     * <br>
     * 校验：<br>
     * 用户名或密码不能为空<br>
     * 用户名不能重复<br>
     * 用户关联角色不能为空<br>
     * 不能关联非法角色：不存在的角色<br>
     * 非管理员不能创建管理员用户<br>
     * <br>
     * 配置：<br>
     * 密码使用BCrypt加密<br>
     * 设置密码更新时间为当前时间--密码过期校验功能依赖这个时间<br>
     * 设置删除标记为：未删除
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
            String msg = MessageFormat.format("用户名重复：{0}!", user.getUserName());
            return AjaxResult.error(msg);
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

        // 设置密码删除时间，以使密码过期检测功能生效
        user.setPwdUpdateTime(new Date());

        userService.insertSelective(user);

        String msg = MessageFormat.format("用户{0}创建成功", user.getUserName());
        return AjaxResult.success(msg);
    }

    /**
     * 获取用户信息详情<br>
     * 鉴权：拥有system:user权限的用户可以访问<br>
     *
     * 处理：对前端隐藏密码
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
     * 删除用户<br>
     * 鉴权：拥有system:user权限的用户可以访问<br>
     * <br>
     * 校验：<br>
     * - 用户主键不能为空<br>
     * - 不能删除自己<br>
     * - 非管理员不能删除管理员用户<br>
     *
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

        String msg = MessageFormat.format("成功删除{0}个账号", deleteCount);
        return AjaxResult.success(msg);
    }

    /**
     * 更新用户<br>
     * 鉴权：拥有system:user权限的用户可以访问<br>
     * <br>
     * 校验：<br>
     * - 用户主键不能为空<br>
     * - 用户ID必须存在<br>
     * - 不能更新自己的用户信息<br>
     * - 非管理员不能更新管理员信息<br>
     * <br>
     * 更新后的操作：<br>
     * - 如果停用了账号，则发送站内信<br>
     * - 如果启用了账号，则发送站内信<br>
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
            String msg = MessageFormat.format("待更新用户(id={0})不存在!", user.getId());
            return AjaxResult.error(msg);
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

        // 站内信
        boolean is_updateUser_disabled = UserConstants.STATUS_DISABLE == user.getStatus();
        boolean is_dbUser_enable = UserConstants.STATUS_ENABLE == existUser.getStatus();
        boolean is_dbUser_disabled = !is_dbUser_enable;
        boolean is_updateUser_enable = !is_updateUser_disabled;
        // 用户停用站内信
        if (is_dbUser_enable && is_updateUser_disabled) {
            msgService.insertAdminDisableUserMessage(loginUser.getUser().getNickName(), loginUser.getUser().getId(),
                    existUser.getNickName(), existUser.getId());
        }
        // 用户启用站内信
        if (is_dbUser_disabled && is_updateUser_enable) {
            msgService.insertAdminEnableUserMessage(loginUser.getUser().getNickName(), loginUser.getUser().getId(),
                    existUser.getNickName(), existUser.getId());
        }

        return AjaxResult.success();
    }

    /**
     * 修改用户密码<br>
     * 鉴权：拥有system:user权限的用户可以访问
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

        msgService.insertAdminUpdatePasswordMessage(loginUser.getUser().getNickName(), loginUser.getUser().getId(),
                existUser.getNickName(), existUser.getId());

        return AjaxResult.success();
    }

}
