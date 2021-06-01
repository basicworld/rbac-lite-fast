package com.rbac.system.service;

import java.util.List;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysUser;

/**
 * 用户service
 *
 * @author wlfei
 * @date 2021-04-14
 */
public interface ISysUserService extends BaseService<SysUser> {

    /**
     * 判断用户密码过期情况<br>
     *
     *
     *
     * @param userId      用户id
     * @param sendMessage true-过期发送站内信 false-不发送
     * @return true-过期<br>
     *         false-不过期
     */
    Boolean checkIfPasswordExpired(Long userId, boolean sendMessage);

    /**
     * 判断用户密码过期情况<br>
     *
     *
     * @param user        用户对象
     * @param sendMessage true-过期发送站内信 false-不发送
     * @return true-过期<br>
     *         false-不过期
     */
    Boolean checkIfPasswordExpired(SysUser user, boolean sendMessage);

    /**
     * 按条筛选用户列表<br>
     * 支持的筛选条件：<br>
     * - userName 用户名，全模糊查询<br>
     * - nickName 昵称，全模糊查询<br>
     * - email 邮箱，全模糊查询<br>
     * - phone 手机号，全模糊查询<br>
     * <br>
     * 如果条件为空，则返回所有用户
     *
     * @param user{userName, nickName, email, phone}
     * @return
     */
    List<SysUser> listByUser(SysUser user);

    /**
     * 按用户名等值查询用户列表<br>
     * 用户名不允许重复，返回列表最多包含一个元素
     *
     * @param userName
     * @return
     */
    List<SysUser> listbyUserNameEqualsTo(String userName);

    /**
     * 检验用户ID是否是超级管理员<br>
     * 原理：判断用户的角色列表中，是否包含超级管理员角色
     *
     * @param userId
     * @return true--是超级管理员<br>
     *         false--不是超级管理员
     */
    Boolean isAdmin(Long userId);

    /**
     * 检验用户ID是否不是超级管理员<br>
     *
     * @param userId
     * @return true--不是超级管理员<br>
     *         false--是超级管理员
     */
    Boolean isNotAdmin(Long userId);

    /**
     * 根据用户ID更新密码，同时更新“密码更新时间”<br>
     *
     *
     * @param user{id, password}
     * @return
     */
    Integer updatePasswordByPrimaryKey(SysUser user);

    /**
     * 根据用户ID批量、物理删除用户<br>
     * 事务
     *
     * @param userIds 用户ID列表
     * @return
     */
    Integer deleteByPrimaryKey(List<Long> userIds);

    /**
     * 根据用户ID更新用户信息<br>
     * 选择性更新，只更新非null字段
     *
     * @param user
     */
    Integer updatePersonalInfoSelective(SysUser user);

}
