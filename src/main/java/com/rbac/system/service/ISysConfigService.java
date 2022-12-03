/**
 *
 */
package com.rbac.system.service;

import java.util.List;
import java.util.stream.Collectors;

import com.rbac.system.base.BaseService;
import com.rbac.system.domain.SysConfig;
import com.rbac.system.domain.dto.SysConfigDTO;

/**
 * 系统配置service<br>
 * 缓存机制：<br>
 * 启动springboot时，从数据库读取配置信息，写入缓存<br>
 * 用户可以手动触发刷新缓存，执行步骤同上
 *
 * @author wlfei
 * @date 2021-05-08
 */
public interface ISysConfigService extends BaseService<SysConfig> {
    /**
     * 更新配置信息
     * 
     * @param configList
     * @return
     */
    public Integer updateSelective(List<SysConfig> configList);

    /**
     * 通过configKey精确查询配置项<br>
     *
     * @param configKey
     * @return SysConfig()--查找到时<br>
     *         null--未找到时
     */
    SysConfig selectByConfigKey(String configKey);

    /**
     * 获取可见的配置项<br>
     *
     *
     * @param queryParam
     * @return
     */
    List<SysConfig> listVisibleConfig(SysConfig queryParam);

    /**
     * 获取所有的配置项<br>
     *
     *
     * @return
     */
    List<SysConfig> listAllConfig();

    /**
     * 获取指定configKey的configValue<br>
     * 原理：先从redis缓存获取，缓存没有的话再从数据库获取<br>
     *
     *
     * @param configKey
     * @param defaultConfigValue 默认值
     * @return configValue非空时，返回configValue<br>
     *         configValue为空(null、空字符、全空格字符判定为空)时，返回defaultConfigValue
     */
    String valueOfConfig(String configKey, String defaultConfigValue);

    /**
     * 获取指定configKey的configValue<br>
     * 原理：先从redis缓存获取，缓存没有的话再从数据库获取<br>
     *
     *
     * @param configKey
     * @param defaultConfigValue 默认值
     * @return configValue非空时，返回configValue<br>
     *         configValue为空时，返回defaultConfigValue
     */
    Integer valueOfConfig(String configKey, Integer defaultConfigValue);

    /**
     * 获取指定configKey的configValue<br>
     * 原理：先从redis缓存获取，缓存没有的话再从数据库获取<br>
     *
     *
     * @param configKey
     * @param defaultConfigValue 默认值
     * @return configValue非空时，返回configValue<br>
     *         configValue为空时，返回defaultConfigValue
     */
    Byte valueOfConfig(String configKey, Byte defaultConfigValue);

    /**
     * 将SysConfig对象转换为SysConfigDTO对象<br>
     * configList不能为null
     *
     *
     * @param configList
     * @return
     */
    default List<SysConfigDTO> do2dto(List<SysConfig> configList) {
        return configList.stream().map(e -> do2dto(e)).collect(Collectors.toList());
    }

    /**
     * 将SysConfig对象转换为SysConfigDTO对象<br>
     * conf不能为null
     *
     *
     * @param conf
     * @return
     */
    default SysConfigDTO do2dto(SysConfig conf) {
        SysConfigDTO dto = new SysConfigDTO();
        dto.setId(conf.getId());
        dto.setConfigKey(conf.getConfigKey());
        dto.setConfigName(conf.getConfigName());
        dto.setConfigValue(conf.getConfigValue());
        dto.setFormType(conf.getFormType());
        dto.setMultiple(conf.getMultiple());
        dto.setNote(conf.getNote());
        dto.setOptionalValues(conf.getOptionalValues());
        dto.setSort(conf.getSort());

        return dto;
    }

    /**
     * 刷新缓存的配置信息<br>
     * 把数据库的新配置写入缓存
     *
     * @return 刷新的配置项个数
     */
    Integer flushCache();

}
