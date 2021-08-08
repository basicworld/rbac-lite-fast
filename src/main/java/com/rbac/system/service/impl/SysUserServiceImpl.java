package com.rbac.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbac.common.util.sql.SqlUtil;
import com.rbac.system.constant.ConfigConstants;
import com.rbac.system.constant.RoleConstants;
import com.rbac.system.domain.SysRole;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.SysUserExample;
import com.rbac.system.domain.SysUserRole;
import com.rbac.system.mapper.SysUserMapper;
import com.rbac.system.service.ISysConfigService;
import com.rbac.system.service.ISysMessageService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    SysUserMapper userMapper;
    @Autowired
    ISysUserRoleService userRoleService;
    @Autowired
    ISysRoleService roleService;
    @Autowired
    ISysMessageService messageService;

    @Autowired
    ISysConfigService configService;

    @Override
    public Integer insertSelective(SysUser user) {
        user.setCreateTime(new Date());
        Integer result = userMapper.insertSelective(user);

        updateRoleRelationForUser(user);

        return result;
    }

    @Override
    @Transactional
    public Integer deleteByPrimaryKey(Long userId) {
        userRoleService.deleteByUserId(userId);
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    @Transactional
    public Integer deleteByPrimaryKey(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return 0;
        }

        int count = 0;
        for (Long userId : userIds) {
            count += deleteByPrimaryKey(userId);
        }
        return count;
    }

    @Override
    @Transactional
    public Integer updateSelective(SysUser user) {
        user.setUpdateTime(new Date());

        updateRoleRelationForUser(user);

        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer updatePersonalInfoSelective(SysUser user) {
        user.setUpdateTime(new Date());

        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer updatePasswordByPrimaryKey(SysUser user) {
        user.setUpdateTime(new Date());
        Integer updateResult = userMapper.updateByPrimaryKeySelective(user);

        return updateResult;
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {

        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<SysUser> listByUser(SysUser user) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria c1 = example.createCriteria();

        if (null != user) {
            if (StringUtils.isNotBlank(user.getUserName())) {
                c1.andUserNameLike(SqlUtil.getFuzzQueryParam(user.getUserName()));
            }
            if (StringUtils.isNotBlank(user.getNickName())) {
                c1.andNickNameLike(SqlUtil.getFuzzQueryParam(user.getNickName()));
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                c1.andEmailLike(SqlUtil.getFuzzQueryParam(user.getEmail()));
            }
            if (StringUtils.isNotBlank(user.getPhone())) {
                c1.andPhoneLike(SqlUtil.getFuzzQueryParam(user.getPhone()));
            }
        }
        return userMapper.selectByExample(example);
    }

    @Override
    public List<SysUser> listbyUserNameEqualsTo(String userName) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria c1 = example.createCriteria();

        c1.andUserNameEqualTo(userName);
        return userMapper.selectByExample(example);
    }

    @Override
    public Boolean isAdmin(Long userId) {
        List<Long> roleIds = userRoleService.listByUserId(userId).stream().map(v -> v.getRoleId())
                .collect(Collectors.toList());
        for (SysRole roleOfUser : roleService.listByRoleId(roleIds)) {
            if (RoleConstants.ADMIN_ROLE_KEY.equals(roleOfUser.getRoleKey())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isNotAdmin(Long userId) {
        return !isAdmin(userId);
    }

    /**
     * 删除旧的 用户--角色关系，创建新的 用户--角色关系
     *
     * @param user
     * @return
     */
    private Integer updateRoleRelationForUser(SysUser user) {
        if (null == user || null == user.getId()) {
            return 0;
        }
        // 根据用户ID删除关系
        userRoleService.deleteByUserId(user.getId());

        // 创建新的关系
        int count = 0;
        if (CollectionUtils.isNotEmpty(user.getRoleIds())) {
            for (Long roleId : user.getRoleIds()) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(roleId);
                count += userRoleService.insertSelective(ur);
            }
        }
        return count;
    }

    @Override
    public Boolean checkIfPasswordExpired(Long userId, boolean sendMessage) {
        // 从数据库获取用户信息反馈
        SysUser userInDB = selectByPrimaryKey(userId);
        return checkIfPasswordExpired(userInDB, sendMessage);
    }

    @Override
    public Boolean checkIfPasswordExpired(SysUser user, boolean sendMessage) {
        final int DEFAULT_EXPIRE_TIME = 30;
        int maxExpireDays = configService.valueOfConfig(ConfigConstants.KEY_USER_PASSWORD_EXPIRE_TIME,
                DEFAULT_EXPIRE_TIME);
        // 判断密码是否过期
        if (null != user.getPwdUpdateTime()) {
            Date now = new Date();
            Date targetExpiredDate = DateUtils.addDays(user.getPwdUpdateTime(), maxExpireDays);
            boolean is_expired = now.compareTo(targetExpiredDate) > 0;
            if (is_expired && sendMessage) {
                // 站内信通知
                messageService.insertPersonalPasswordExpireMessage(user.getNickName(), user.getId(), maxExpireDays);
                if (logger.isWarnEnabled()) {
                    logger.warn("用户{}密码已过期{}天以上，已发送站内信", user.getUserName(), maxExpireDays);
                }
            }
            return is_expired;
        }
        return false;
    }

}
