/**
 *
 */
package com.rbac.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbac.common.util.BaseUtils;
import com.rbac.framework.redis.RedisCache;
import com.rbac.system.constant.ConfigConstants;
import com.rbac.system.domain.SysConfig;
import com.rbac.system.domain.SysConfigExample;
import com.rbac.system.mapper.SysConfigMapper;
import com.rbac.system.service.ISysConfigService;

/**
 * 系统配置service实现类
 *
 * @author wlfei
 * @date 2021-05-08
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    /** 默认配置值 仅用于标识 不作为真正的返回值 */
    private static final String DEFAULT_VALUE = "__default_value__";

    @Autowired
    SysConfigMapper configMapper;

    @Autowired
    RedisCache redisCache;

    @Override
    public Integer insertSelective(SysConfig config) {
        if (null == config.getCreateTime()) {
            config.setUpdateTime(new Date());
        }
        return configMapper.insertSelective(config);
    }

    @Override
    public Integer deleteByPrimaryKey(Long configId) {

        return configMapper.deleteByPrimaryKey(configId);
    }

    @Override
    @Transactional
    public Integer updateSelective(List<SysConfig> configList) {
        int updateCount = 0;
        for (SysConfig config : configList) {
            updateCount += updateSelective(config);
        }
        return updateCount;
    }

    @Override
    public Integer updateSelective(SysConfig config) {
        if (null == config.getUpdateTime()) {
            config.setUpdateTime(new Date());
        }
        return configMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public SysConfig selectByPrimaryKey(Long configId) {

        SysConfig conf = configMapper.selectByPrimaryKey(configId);

        conf.setMultiple(isMultiple(conf));
        return conf;
    }

    @Override
    public SysConfig selectByConfigKey(String configKey) {
        SysConfigExample example = new SysConfigExample();
        example.createCriteria().andConfigKeyEqualTo(configKey);
        List<SysConfig> list = configMapper.selectByExample(example);
        SysConfig conf = BaseUtils.firstItemOfList(list);

        conf.setMultiple(isMultiple(conf));
        return conf;
    }

    /**
     * 判断是否为多选项<br>
     * 原理：config.configValueType如果是复选框，则为多选，否则不是多选
     *
     * @param conf
     * @return ConfigConstants.MULTIPLE_YES 是<br>
     *         ConfigConstants.MULTIPLE_NO 否
     */
    private Byte isMultiple(SysConfig conf) {
        if (null == conf) {
            return ConfigConstants.MULTIPLE_NO;
        }
        if (ConfigConstants.FORM_TYPE_CHECKBOX.equalsIgnoreCase(conf.getConfigValueType())) {
            return ConfigConstants.MULTIPLE_YES;
        }
        return ConfigConstants.MULTIPLE_NO;
    }

    @Override
    public List<SysConfig> listVisibleConfig(SysConfig queryParam) {
        SysConfigExample example = new SysConfigExample();
        example.createCriteria().andVisibleEqualTo(ConfigConstants.VISIBLE_YES);
        return configMapper.selectByExample(example);
    }

    @Override
    public List<SysConfig> listAllConfig() {
        return configMapper.selectByExample(new SysConfigExample());
    }

    @Override
    public String valueOfConfig(String configKey, String defaultConfigValue) {
        // 从缓存获取
        String cacheKey = getCacheConfigKey(configKey);
        String cacheValue = redisCache.getCacheObject(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            return cacheValue;
        }
        // 从数据库获取
        SysConfig conf = selectByConfigKey(configKey);
        if (null == conf || StringUtils.isBlank(conf.getConfigValue())) {
            return defaultConfigValue;
        }
        return conf.getConfigValue();
    }

    @Override
    public Integer valueOfConfig(String configKey, Integer defaultConfigValue) {
        String value = valueOfConfig(configKey, DEFAULT_VALUE);
        if (DEFAULT_VALUE.contentEquals(value)) {
            return defaultConfigValue;
        }
        return Integer.parseInt(value);
    }

    @Override
    public Byte valueOfConfig(String configKey, Byte defaultConfigValue) {
        String value = valueOfConfig(configKey, DEFAULT_VALUE);
        if (DEFAULT_VALUE.contentEquals(value)) {
            return defaultConfigValue;
        }
        return Byte.parseByte(value);
    }

    @Override
    public Integer flushCache() {
        List<SysConfig> allConfigList = listAllConfig();
        // 如果数据库的配置项数量为0 则终止执行
        if (null == allConfigList || allConfigList.isEmpty()) {
            return 0;
        }
        // redis缓存中只缓存配置项的键+值
        // 键和值的类型都是字符串
        for (SysConfig config : allConfigList) {
            String cacheKey = getCacheConfigKey(config.getConfigKey());
            String cacheValue = config.getConfigValue();
            redisCache.setCacheObject(cacheKey, cacheValue);
        }

        int total = allConfigList.size();
        return total;
    }

    /**
     * 生成redis键值对的键
     *
     *
     * @param configKey
     * @return
     */
    private String getCacheConfigKey(String configKey) {
        return ConfigConstants.CONFIG_CACHE_PREFIX + configKey;
    }

}
