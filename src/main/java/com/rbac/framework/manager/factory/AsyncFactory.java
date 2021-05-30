/**
 *
 */
package com.rbac.framework.manager.factory;

import java.util.TimerTask;

import com.rbac.common.util.spring.SpringUtils;
import com.rbac.system.service.IMailService;

/**
 * 异步工厂<br>
 * 实现功能：异步发送邮件
 *
 * @author wlfei
 * @date 2021-05-10
 */
public class AsyncFactory {
    /**
     * 发送简单文本邮件
     *
     * @param to      收件箱
     * @param subject 邮件标题
     * @param content 邮件内容
     * @return
     */
    public static TimerTask sendSimpleMail(final String to, final String subject, final String content) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(IMailService.class).sendSimpleMail(to, subject, content);
            }
        };
    }
}
