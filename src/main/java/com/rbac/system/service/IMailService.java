/**
 * 
 */
package com.rbac.system.service;

/**
 * 邮箱service
 * 
 * @author wlfei
 * @date 2021-05-10
 */
public interface IMailService {
    /**
     * 确认是否可以发邮件
     * 
     * @return
     */
    Boolean canSendMail();

    /**
     * 使用缓存配置，更新邮箱配置参数和邮箱连接<br>
     * 一般用于动态修改smtp相关参数
     * 
     * @return
     */
    Boolean reloadMailConfigFromCache();

    /**
     * 发送纯文本邮件
     * 
     * @param to
     * @param subject
     * @param content
     * @return true-发送成功 false-发送失败 null-其他异常发送失败
     */
    Boolean sendSimpleMail(String to, String subject, String content);

}
