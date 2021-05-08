/**
 * 
 */
package com.rbac.system.service;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysConfig;

/**
 * 系统配置service
 * 
 * @author wlfei
 * @date 2021-05-08
 */
public interface ISysConfigService extends BaseService<SysConfig> {
    /**
     * 通过configKey精确查询配置项<br>
     * 
     * @param configKey
     * @return SysConfig()--查找到时<br>
     *         null--未找到时
     */
    SysConfig selectByConfigKey(String configKey);
}
