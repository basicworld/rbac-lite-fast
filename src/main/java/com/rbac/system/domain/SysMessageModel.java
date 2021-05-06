package com.rbac.system.domain;

import java.util.Date;

public class SysMessageModel {
    private Long id;

    private String modelKey;

    private String titleModel;

    private String titleModelDesc;

    private String contentModel;

    private String contentModelDesc;

    private Byte sendSystemMessage;

    private Byte sendSms;

    private Byte sendEmail;

    private Byte enable;

    private String note;

    private String createBy;

    private Date createTime;

    private String updateBy;

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

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
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