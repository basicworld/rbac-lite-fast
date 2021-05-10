/**
 * 
 */
package com.rbac.system.domain.dto;

/**
 * 系统配置dto
 * 
 * @author wlfei
 * @date 2021-05-09
 */
public class SysConfigDTO {
	private Long id;

	private String configName;

	private String configKey;

	private String configValue;

	private String formType;

	private String optionalValues;

	private Integer sort;

	private String note;

	/** 是否为多选项 */
	private Byte multiple;

	public Long getId() {
		return id;
	}

	public String getConfigName() {
		return configName;
	}

	public String getConfigKey() {
		return configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public String getFormType() {
		return formType;
	}

	public String getOptionalValues() {
		return optionalValues;
	}

	public Integer getSort() {
		return sort;
	}

	public String getNote() {
		return note;
	}

	public Byte getMultiple() {
		return multiple;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public void setOptionalValues(String optionalValues) {
		this.optionalValues = optionalValues;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setMultiple(Byte multiple) {
		this.multiple = multiple;
	}

	@Override
	public String toString() {
		return "SysConfigDTO [id=" + id + ", configName=" + configName + ", configKey=" + configKey + ", configValue="
				+ configValue + ", formType=" + formType + ", optionalValues=" + optionalValues + ", sort=" + sort
				+ ", note=" + note + ", multiple=" + multiple + "]";
	}

}
