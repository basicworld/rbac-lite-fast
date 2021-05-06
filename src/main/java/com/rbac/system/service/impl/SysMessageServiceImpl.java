/**
 * 
 */
package com.rbac.system.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbac.system.constant.MessageConstants;
import com.rbac.system.domain.SysMessage;
import com.rbac.system.domain.SysMessageExample;
import com.rbac.system.domain.SysMessageModel;
import com.rbac.system.domain.SysUser;
import com.rbac.system.mapper.SysMessageMapper;
import com.rbac.system.service.ISysMessageModelService;
import com.rbac.system.service.ISysMessageService;

/**
 * 消息服务 实现类
 * 
 * @author wlfei
 * @date 2021-05-05
 */
@Service
public class SysMessageServiceImpl implements ISysMessageService {
	@Autowired
	SysMessageMapper messageMapper;

	@Autowired
	ISysMessageModelService msgModelService;

	/**
	 * 获取用户的消息列表
	 * 
	 * 
	 * @param userId 用户主键
	 * @return
	 */
	@Override
	public List<SysMessage> listMessageOfUser(Long userId) {
		SysMessageExample example = new SysMessageExample();
		SysMessageExample.Criteria c1 = example.createCriteria();
		c1.andReceiverIdEqualTo(userId);
		c1.andDeletedEqualTo(MessageConstants.DELETE_NO);

		return messageMapper.selectByExample(example);

	}

	@Override
	public Integer markAsRead(List<Long> messageIds) {
		int readCount = 0;
		for (Long messageId : messageIds) {
			readCount += markAsRead(messageId);
		}

		return readCount;
	}

	private int markAsRead(Long messageId) {
		SysMessage item = new SysMessage();
		item.setId(messageId);
		item.setHasRead(MessageConstants.READ_DOWN);
		item.setUpdateTime(new Date());

		return messageMapper.updateByPrimaryKey(item);
	}

	@Override
	public Long countUnreadMessage(Long userId) {
		SysMessageExample example = new SysMessageExample();
		SysMessageExample.Criteria c1 = example.createCriteria();
		c1.andReceiverIdEqualTo(userId);
		c1.andHasReadEqualTo(MessageConstants.UNREAD);
		c1.andDeletedEqualTo(MessageConstants.DELETE_NO);
		return messageMapper.countByExample(example);
	}

	@Override
	public Integer insertSelective(SysMessage item) {
		if (null == item.getCreateTime()) {
			item.setCreateTime(new Date());
		}
		return messageMapper.insertSelective(item);
	}

	@Override
	public Integer deleteByPrimaryKey(Long id) {

		return messageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer updateSelective(SysMessage item) {
		if (null == item.getCreateTime()) {
			item.setCreateTime(new Date());
		}
		return messageMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public SysMessage selectByPrimaryKey(Long id) {

		return messageMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer insertMessage(String title, String content, String senderName, Long senderID, String receiverName,
			Long receiverID) {
		SysMessage msg = new SysMessage();
		msg.setTitle(title);
		msg.setContent(content);
		msg.setSender(senderName);
		msg.setSenderId(senderID);
		msg.setReceiver(receiverName);
		msg.setReceiverId(receiverID);

		msg.setCreateBy(senderName);
		msg.setCreateTime(new Date());
		msg.setDeleted(MessageConstants.DELETE_NO);
		msg.setVisible(MessageConstants.VISIBLE_YES);
		msg.setHasRead(MessageConstants.UNREAD);
		return messageMapper.insertSelective(msg);
	}

	@Override
	public Integer insertAdminUpdatePasswordMessage(String senderName, Long senderID, String receiverName,
			Long receiverID) {
		SysMessageModel msgModel = msgModelService.selectByModelKey(MessageConstants.MODEL_KEY_ADMIN_CHANGE_PASSWORD);
		if (null == msgModel) {
			return null;
		}
		String content = MessageFormat.format(msgModel.getContentModel(), senderName);

		return insertMessage(msgModel.getTitleModel(), content, senderName, senderID, receiverName, receiverID);
	}

	@Override
	public Integer markMessageAsRead(List<Long> messageIds, SysUser user) {

		// 如果消息ID中包含【全部消息】主键标记，则把这个用户的所有消息标记为已读
		boolean needMarkAllMessage = messageIds.contains(MessageConstants.ALL_MESSAGE_TAG);
		SysMessageExample example = new SysMessageExample();
		if (needMarkAllMessage) {
			example.createCriteria().andReceiverIdEqualTo(user.getId()).andDeletedEqualTo(MessageConstants.DELETE_NO)
					.andHasReadEqualTo(MessageConstants.UNREAD);
		} else {
			example.createCriteria().andReceiverIdEqualTo(user.getId()).andDeletedEqualTo(MessageConstants.DELETE_NO)
					.andHasReadEqualTo(MessageConstants.UNREAD).andIdIn(messageIds);
		}
		SysMessage updateParam = new SysMessage();
		updateParam.setHasRead(MessageConstants.READ_DOWN);
		updateParam.setUpdateBy(user.getNickName());
		updateParam.setUpdateTime(new Date());

		return messageMapper.updateByExampleSelective(updateParam, example);

	}

}
