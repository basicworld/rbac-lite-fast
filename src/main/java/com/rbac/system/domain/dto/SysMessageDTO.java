package com.rbac.system.domain.dto;

/**
 * 消息DTO
 *
 * @author wlfei
 * @date 2021-05-07
 */
public class SysMessageDTO {
    private Long id;

    private String sender;

    private Long senderId;

    private String receiver;

    private Long receiverId;

    private String title;

    private String content;

    private Byte hasRead;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    public Long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getReceiver() {
        return receiver;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Byte getHasRead() {
        return hasRead;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setHasRead(Byte hasRead) {
        this.hasRead = hasRead;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysMessageDTO [id=" + id + ", sender=" + sender + ", senderId=" + senderId + ", receiver=" + receiver
                + ", receiverId=" + receiverId + ", title=" + title + ", content=" + content + ", hasRead=" + hasRead
                + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime="
                + updateTime + "]";
    }

}
