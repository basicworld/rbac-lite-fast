package com.rbac.system.domain;

import java.util.Date;

/**
 * 系统配置项
 *
 * @author wlfei
 * @date 2021-05-30
 */
public class SysConfig {
    /** 主键 */
    private Long id;

    /** 配置项名称 */
    private String configName;

    /** 配置项关键字 唯一标识符 */
    private String configKey;

    /** 配置值 */
    private String configValue;

    /** 配置值的类型 */
    private String configValueType;

    /** 系统配置项标志 */
    private Byte systemBuilt;

    /** 前端展示的表单类型 */
    private String formType;

    /** 配置值的可选域 多选或单选类的配置项用 */
    private String optionalValues;

    /** 前端可见性 */
    private Byte visible;

    /** 前端排序 */
    private Integer sort;

    /** 默认值 */
    private String defaultValue;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 描述 */
    private String note;

    /** 是否为多值型配置项 */
    private Byte multiple;

    public Byte getMultiple() {
        return multiple;
    }

    public void setMultiple(Byte multiple) {
        this.multiple = multiple;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getConfigValueType() {
        return configValueType;
    }

    public void setConfigValueType(String configValueType) {
        this.configValueType = configValueType == null ? null : configValueType.trim();
    }

    public Byte getSystemBuilt() {
        return systemBuilt;
    }

    public void setSystemBuilt(Byte systemBuilt) {
        this.systemBuilt = systemBuilt;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType == null ? null : formType.trim();
    }

    public String getOptionalValues() {
        return optionalValues;
    }

    public void setOptionalValues(String optionalValues) {
        this.optionalValues = optionalValues == null ? null : optionalValues.trim();
    }

    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}