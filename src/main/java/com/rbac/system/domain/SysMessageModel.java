package com.rbac.system.domain;

import java.util.Date;

/**
 *
 * 系统消息模版
 *
 * @author wlfei
 * @date 2021-05-30
 */
public class SysMessageModel {
    /** 主键 */
    private Long id;

    /** 模版标识符，程序内定，不能修改数据库的这个字段 */
    private String modelKey;

    /** 标题模版 */
    private String titleModel;

    /** 标题模版描述 */
    private String titleModelDesc;

    /** 内容模版 */
    private String contentModel;

    /** 内容模版描述 */
    private String contentModelDesc;

    /** 是否发送站内信 */
    private Byte sendSystemMessage;

    /** 是否发送短信 */
    private Byte sendSms;

    /** 是否发送邮件 */
    private Byte sendEmail;

    /** 备注 */
    private String note;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey == null ? null : modelKey.trim();
    }

    public String getTitleModel() {
        return titleModel;
    }

    public void setTitleModel(String titleModel) {
        this.titleModel = titleModel == null ? null : titleModel.trim();
    }

    public String getTitleModelDesc() {
        return titleModelDesc;
    }

    public void setTitleModelDesc(String titleModelDesc) {
        this.titleModelDesc = titleModelDesc == null ? null : titleModelDesc.trim();
    }

    public String getContentModel() {
        return contentModel;
    }

    public void setContentModel(String contentModel) {
        this.contentModel = contentModel == null ? null : contentModel.trim();
    }

    public String getContentModelDesc() {
        return contentModelDesc;
    }

    public void setContentModelDesc(String contentModelDesc) {
        this.contentModelDesc = contentModelDesc == null ? null : contentModelDesc.trim();
    }

    public Byte getSendSystemMessage() {
        return sendSystemMessage;
    }

    public void setSendSystemMessage(Byte sendSystemMessage) {
        this.sendSystemMessage = sendSystemMessage;
    }

    public Byte getSendSms() {
        return sendSms;
    }

    public void setSendSms(Byte sendSms) {
        this.sendSms = sendSms;
    }

    public Byte getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Byte sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
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
}