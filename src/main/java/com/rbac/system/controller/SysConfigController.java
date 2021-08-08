/**
 *
 */
package com.rbac.system.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rbac.common.util.ServletUtils;
import com.rbac.common.util.valid.ValidUtils;
import com.rbac.framework.manager.AsyncManager;
import com.rbac.framework.manager.factory.AsyncFactory;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.framework.web.page.TableDataInfo;
import com.rbac.system.base.BaseController;
import com.rbac.system.constant.ConfigConstants;
import com.rbac.system.domain.SysConfig;
import com.rbac.system.domain.dto.MailDTO;
import com.rbac.system.domain.dto.SysConfigDTO;
import com.rbac.system.service.IMailService;
import com.rbac.system.service.ISysConfigService;

/**
 * 配置项controller
 *
 * @author wlfei
 * @date 2021-05-08
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
    @Autowired
    ISysConfigService configService;

    @Autowired
    TokenService tokenService;

    @Autowired
    IMailService mailService;

    /**
     * 获取系统所有的配置项<br>
     * 限制最多返回9999个配置项
     *
     * @return TableDataInfo{code, msg, total, rows:[]}
     */
    @GetMapping("/list")
    public TableDataInfo listAll() {
        PageHelper.startPage(ConfigConstants.PAGE_START, ConfigConstants.PAGE_SIZE, ConfigConstants.SORT_KEY);
        List<SysConfig> configList = configService.listVisibleConfig(new SysConfig());
        long total = new PageInfo<SysConfig>(configList).getTotal();
        List<SysConfigDTO> dtoList = configService.do2dto(configList);

        // 对前端隐藏密码类配置项的值
        for (SysConfigDTO dto : dtoList) {
            if (ConfigConstants.FORM_TYPE_PASSWORD.equalsIgnoreCase(dto.getFormType())) {
                dto.setConfigValue("********");
            }
        }

        return getDataTable(dtoList, total);

    }

    /**
     * 刷新缓存，让数据库的新配置项生效
     *
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:config')")
    @PostMapping("/cache/flush")
    public AjaxResult flushCache() {
        // 把数据库的配置项写入redis缓存
        configService.flushCache();
        // 从缓存载入邮箱配置到spring
        mailService.reloadMailConfigFromCache();
        return AjaxResult.success();
    }

    /**
     * 发送测试邮件，测试邮箱参数是否正确<br>
     * 实际是执行了一个后台邮件发送线程，然后告诉前端已发送邮件<br>
     * 是否成功发送邮件，由收件人自行判断
     *
     *
     * @param MailDTO{mailTo, }
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:config')")
    @PostMapping("/test/mailSend")
    public AjaxResult mailSendTest(@RequestBody MailDTO mailDTO) {
        String mailTo = mailDTO.getMailTo();
        if (logger.isDebugEnabled()) {
            logger.debug("测试收件箱：{}", mailTo);
        }
        // 教育收件箱格式
        boolean mailValid = ValidUtils.isValidEmail(mailTo);
        if (false == mailValid) {
            return AjaxResult.error("收件箱格式错误：{}", mailTo);
        }
        // 确认是否可以发送邮件
        if (!mailService.canSendMail()) {
            return AjaxResult.error("邮箱功能未启用，无法发送邮件！");
        }
        // 执行邮件发送线程
        AsyncManager.me()
                .execute(AsyncFactory.sendSimpleMail(mailTo.trim(), "来自RBAC系统的邮件", "收到本邮件表明邮箱参数配置正确，且互联网处于连通状态。"));
        // 构造返回数据
        String msg = MessageFormat.format("已尝试发送测试邮件给{0}，请查收验证", mailTo);
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
        return AjaxResult.success(msg);
    }

    /**
     * 批量更新系统配置<br>
     *
     *
     * @param configDTOList[SysConfigDTO{}, SysConfigDTO{}]
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:config')")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody List<SysConfigDTO> configDTOList) {
        // 入参列表为空时，无需更新
        if (null == configDTOList || configDTOList.isEmpty()) {
            return AjaxResult.error("无需更新");
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();

        List<SysConfig> updateItems = new ArrayList<SysConfig>();
        for (SysConfigDTO dto : configDTOList) {
            // 只能更新部分字段
            SysConfig item = new SysConfig();
            item.setId(dto.getId()); // 按id更新
            item.setConfigValue(dto.getConfigValue()); // 更新值
            item.setSort(dto.getSort()); // 更新排序编号
            item.setUpdateBy(username); // 记录更新者信息

            updateItems.add(item);
        }
        // 批量更新，对于单个配置项，只更新配置项的非null字段
        configService.updateSelective(updateItems);
        return AjaxResult.success();
    }
}
