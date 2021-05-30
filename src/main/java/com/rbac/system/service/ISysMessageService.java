/**
 *
 */
package com.rbac.system.service;

import java.util.ArrayList;
import java.util.List;

import com.rbac.common.util.DateTools;
import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysMessage;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.dto.SysMessageDTO;

/**
 * 用户消息 service
 *
 * @author wlfei
 * @date 2021-05-05
 */
public interface ISysMessageService extends BaseService<SysMessage> {
    /**
     * 插入消息
     *
     *
     * @param title        消息标题
     * @param content      消息内容
     * @param senderName   发送人名称
     * @param senderID     发送人id
     * @param receiverName 接收人名称
     * @param receiverID   接收人id
     * @return 插入个数，一般为1
     */
    Integer insertMessage(String title, String content, String senderName, Long senderID, String receiverName,
            Long receiverID);

    /**
     * 创建 管理员更新密码通知
     *
     *
     * @param senderName   管理员名称
     * @param senderID     管理员id
     * @param receiverName 用户名称
     * @param receiverID   用户id
     * @return
     */
    Integer insertAdminUpdatePasswordMessage(String senderName, Long senderID, String receiverName, Long receiverID);

    /**
     * 创建 管理员停用用户通知
     *
     *
     * @param senderName
     * @param senderID
     * @param receiverName
     * @param receiverID
     * @return
     */
    Integer insertAdminDisableUserMessage(String senderName, Long senderID, String receiverName, Long receiverID);

    /**
     * 创建 管理员启用用户通知
     *
     *
     * @param senderName
     * @param senderID
     * @param receiverName
     * @param receiverID
     * @return
     */
    Integer insertAdminEnableUserMessage(String senderName, Long senderID, String receiverName, Long receiverID);

    /**
     * 创建 管理员修改用户权限通知
     *
     *
     * @param senderName
     * @param senderID
     * @param receiverName
     * @param receiverID
     * @return
     */
    Integer insertAdminChangeUserPermissionMessage(String senderName, Long senderID, String receiverName,
            Long receiverID);

    /**
     * 创建 用户密码过期通知
     *
     *
     * @param personName  用户昵称
     * @param personID    用户id
     * @param expiredDays 过期时间，单位：天
     * @return
     */
    Integer insertPersonalPasswordExpireMessage(String personName, Long personID, Integer expiredDays);

    /**
     * 创建 用户修改个人信息通知
     *
     *
     * @param personName 用户昵称
     * @param personID   用户id
     * @return
     */
    Integer insertPersonalChangeInfoMessage(String personName, Long personID);

    /**
     * 创建 用户修改密码通知
     *
     *
     * @param personName 用户昵称
     * @param personID   用户id
     * @return
     */
    Integer insertPersonalChangePasswordMessage(String personName, Long personID);

    /**
     * 查询指定用户的未读、且未删除消息数量
     *
     *
     * @param userId
     * @return
     */
    Long countUnreadMessage(Long userId);

    /**
     * 列出指定用户的消息
     *
     * @param userId
     * @return
     */
    List<SysMessage> listMessageOfUser(Long userId);

    /**
     * 将给定消息id的信息标记为已读<br>
     *
     *
     * @param messageIds
     * @return
     */
    Integer markAsRead(List<Long> messageIds);

    /**
     * 按消息id将消息标记为已读<br>
     * 如果messageIds中包含-1，则把这个用户所有消息标记为已读
     *
     * @param messageIds 消息主键
     * @param user       消息所属用户
     */
    Integer markMessageAsRead(List<Long> messageIds, SysUser user);

    /**
     * do对象转dto
     *
     *
     *
     * @param messageList
     * @return
     */
    default List<SysMessageDTO> do2dto(List<SysMessage> messageList) {
        List<SysMessageDTO> dtoList = new ArrayList<SysMessageDTO>();
        for (SysMessage message : messageList) {
            dtoList.add(do2dto(message));
        }
        return dtoList;
    }

    /**
     * do对象转dto<br>
     *
     * 处理：<br>
     * - Date()对象转为字符串
     *
     *
     * @param message
     * @return
     */
    default SysMessageDTO do2dto(SysMessage message) {
        SysMessageDTO dto = new SysMessageDTO();
        dto.setId(message.getId());
        dto.setSender(message.getSender());
        dto.setSenderId(message.getSenderId());
        dto.setReceiver(message.getReceiver());
        dto.setReceiverId(message.getReceiverId());
        dto.setTitle(message.getTitle());
        dto.setContent(message.getContent());
        dto.setHasRead(message.getHasRead());
        dto.setCreateBy(message.getCreateBy());

        if (null != message.getCreateTime()) {
            String createTime = DateTools.date2FullStringTime(message.getCreateTime());
            dto.setCreateTime(createTime);
        }
        dto.setUpdateBy(message.getUpdateBy());

        if (null != message.getUpdateTime()) {
            String updateTime = DateTools.date2FullStringTime(message.getUpdateTime());
            dto.setUpdateTime(updateTime);
        }
        return dto;
    }
}
