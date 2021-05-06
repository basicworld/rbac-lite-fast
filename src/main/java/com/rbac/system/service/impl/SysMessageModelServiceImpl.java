/**
 * 
 */
package com.rbac.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbac.common.util.BaseUtils;
import com.rbac.system.domain.SysMessageModel;
import com.rbac.system.domain.SysMessageModelExample;
import com.rbac.system.mapper.SysMessageModelMapper;
import com.rbac.system.service.ISysMessageModelService;

/**
 * 消息模版service 实现
 * 
 * @author wlfei
 * @date 2021-05-06
 */
@Service
public class SysMessageModelServiceImpl implements ISysMessageModelService {
	@Autowired
	SysMessageModelMapper messageModelMapper;

	@Override
	public SysMessageModel selectByModelKey(String modelKey) {
		SysMessageModelExample example = new SysMessageModelExample();
		example.createCriteria().andModelKeyEqualTo(modelKey);
		List<SysMessageModel> list = messageModelMapper.selectByExample(example);
		return BaseUtils.firstItemOfList(list);
	}

}
