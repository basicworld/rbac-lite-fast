/**
 * 
 */
package com.rbac.system.service;

import com.rbac.system.domain.SysMessageModel;

/**
 * 消息模版service
 * 
 * @author wlfei
 * @date 2021-05-06
 */
public interface ISysMessageModelService {
	/**
	 * 根据模版标识id 查询消息模版
	 * 
	 * 
	 * @param modelKey
	 * @return
	 */
	SysMessageModel selectByModelKey(String modelKey);
}
