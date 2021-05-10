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

    @GetMapping("/list")
    public TableDataInfo listAll() {
        PageHelper.startPage(ConfigConstants.PAGE_START, ConfigConstants.PAGE_SIZE, ConfigConstants.SORT_KEY);
        List<SysConfig> configList = configService.listVisibleConfig(new SysConfig());
        long total = new PageInfo<SysConfig>(configList).getTotal();
        List<SysConfigDTO> dtoList = configService.do2dto(configList);

        for (SysConfigDTO dto : dtoList) {
            if (ConfigConstants.FORM_TYPE_PASSWORD.equalsIgnoreCase(dto.getFormType())) {
                dto.setConfigValue("********");
            }
        }

        return getDataTable(dtoList, total);

    }

    @PreAuthorize("@ss.hasPermi('system:config')")
    @PostMapping("/cache/flush")
    public AjaxResult flushCache() {
        configService.flushCache();
        // 从缓存载入邮箱配置到spring
        mailService.reloadMailConfigFromCache();
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('system:config')")
    @PostMapping("/test/mailSend")
    public AjaxResult mailSendTest(@RequestBody MailDTO mailDTO) {
        String mailTo = mailDTO.getMailTo();
        if (logger.isDebugEnabled()) {
            logger.debug("测试收件箱：" + mailTo);
        }
        boolean mailValid = ValidUtils.isValidEmail(mailTo);
        if (false == mailValid) {
            return AjaxResult.error("收件箱格式错误：" + mailTo);
        }
        if (!mailService.canSendMail()) {
            return AjaxResult.error("邮箱功能未启用，无法发送邮件！");
        }
        AsyncManager.me()
                .execute(AsyncFactory.sendSimpleMail(mailTo.trim(), "来自RBAC系统的邮件", "收到本邮件表明邮箱参数配置正确，且网络处于联通状态。"));
        String msg = MessageFormat.format("测试邮件已发送给 {0}，请注意查收", mailTo);
        return AjaxResult.success(msg);
    }

    @PreAuthorize("@ss.hasPermi('system:config')")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody List<SysConfigDTO> configDTOList) {
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
            item.setConfigValue(dto.getConfigValue());
            item.setSort(dto.getSort());
            item.setUpdateBy(username);

            updateItems.add(item);
        }
        configService.updateSelective(updateItems);
        return AjaxResult.success();
    }
}
