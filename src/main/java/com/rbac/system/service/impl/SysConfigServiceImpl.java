/**
 * 
 */
package com.rbac.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbac.common.util.BaseUtils;
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
    @Autowired
    SysConfigMapper configMapper;

    @Override
    public Integer insertSelective(SysConfig item) {
        if (null == item.getCreateTime()) {
            item.setUpdateTime(new Date());
        }
        return configMapper.insertSelective(item);
    }

    @Override
    public Integer deleteByPrimaryKey(Long id) {

        return configMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateSelective(SysConfig item) {
        if (null == item.getUpdateTime()) {
            item.setUpdateTime(new Date());
        }
        return configMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public SysConfig selectByPrimaryKey(Long id) {

        SysConfig conf = configMapper.selectByPrimaryKey(id);

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
     * 判断是否为多选项
     * 
     * @param conf
     * @return ConfigConstants.MULTIPLE_YES 是<br>
     *         ConfigConstants.MULTIPLE_NO 否
     */
    private Byte isMultiple(SysConfig conf) {
        if (null == conf) {
            return null;
        }
        if (ConfigConstants.FORM_TYPE_CHECKBOX.equalsIgnoreCase(conf.getConfigValueType())) {
            return ConfigConstants.MULTIPLE_YES;
        }
        return ConfigConstants.MULTIPLE_NO;
    }

}
