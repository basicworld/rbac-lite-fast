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
     * 确认是否可以发邮件<br>
     * 原理：<br>
     * 系统配置项中有一项是“是否开启邮件功能”，通过这个值来判断<br>
     * 优先从redis缓存取值，redis值为空时从数据库取值
     *
     * @return true--可以发邮件<br>
     *         false--不能发邮件
     */
    Boolean canSendMail();

    /**
     * 更新邮箱配置参数的缓存到spring环境中<br>
     * 一般用于动态（不重启系统）修改smtp相关参数<br>
     * 邮箱配置参数来源：<br>
     * - 优先从redis缓存获取<br>
     * - redis为空时从数据库获取<br>
     * - 数据库为空时，使用默认host=localhsot port=25 无账号密码 不做ssl加密
     *
     * @return true--配置成功<br>
     *         false--配置失败<br>
     */
    Boolean reloadMailConfigFromCache();

    /**
     * 发送纯文本邮件<br>
     * 因为邮件发送比较慢，这个功能适合用单独线程调用，不要在主线程中调用，否则会造成主线程阻塞<br>
     *
     * @param to      一个收件箱
     * @param subject 主题
     * @param content 内容
     * @return true-发送成功<br>
     *         false-发送失败 null-其他异常发送失败
     */
    Boolean sendSimpleMail(String to, String subject, String content);

}
