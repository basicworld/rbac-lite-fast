/**
 *
 */
package com.rbac.system.controller;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.rbac.common.constant.SqlConstants;
import com.rbac.common.util.ServletUtils;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.framework.web.page.TableDataInfo;
import com.rbac.system.base.BaseController;
import com.rbac.system.constant.MessageConstants;
import com.rbac.system.domain.SysMessage;
import com.rbac.system.domain.dto.SysMessageQuery;
import com.rbac.system.service.ISysMessageService;

/**
 * 消息中心controller
 *
 * @author wlfei
 * @date 2021-05-06
 */
@RestController
@RequestMapping("/message")
public class SysMessageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysMessageController.class);

    @Autowired
    ISysMessageService msgService;

    @Autowired
    TokenService tokenService;

    /**
     * 未读消息计数<br>
     * 无需任何入参，通过token来识别用户身份
     *
     * @return 登录用户的未读消息数量
     */
    @GetMapping("/unread/count")
    public AjaxResult countUnreadMessage() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long count = msgService.countUnreadMessage(loginUser.getUser().getId());
        if (null == count) {
            return AjaxResult.success();
        }
        return AjaxResult.success(count);
    }

    /**
     * 标记为已读<br>
     * 如果入参id中包含-1，则把该用户的所有消息标记为已读<br>
     * 不包含-1时，把入参指定的消息标记为已读
     *
     * @param messageIds [id1,id2,id3...] 批量消息ID
     * @return 操作成功
     */
    @PostMapping("/markRead")
    public AjaxResult markRead(@RequestBody List<Long> messageIds) {

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        msgService.markMessageAsRead(messageIds, loginUser.getUser());
        return AjaxResult.success();
    }

    /**
     * 消息详情<br>
     * 消息不存在时，返回错误<br>
     * 根据token判断用户身份，如果用户非法获取其他用户的消息，则返回错误
     *
     *
     * @param messageId 消息ID
     * @return
     */
    @GetMapping("/detail/{messageId}")
    public AjaxResult detail(@PathVariable Long messageId) {
        SysMessage msg = msgService.selectByPrimaryKey(messageId);
        if (null == msg || MessageConstants.DELETE_YES == msg.getDeleted()) {
            return AjaxResult.error("消息不存在");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (loginUser.getUser().getId().compareTo(msg.getReceiverId()) != 0) {
            logger.warn("警告：{}非法获取其他用户消息", loginUser.getUsername());
            return AjaxResult.error("非法操作！");
        }
        return AjaxResult.success(msgService.do2dto(msg));
    }

    /**
     * 分页获取消息列表<br>
     * 默认按照消息时间的倒序排列返回结果<br>
     * 如果指定了“未读消息置顶”，则按未读消息优先、且时间倒序排列返回结果
     *
     *
     * @param queryParams
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(SysMessageQuery queryParams) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 自定义sql排序
        String orderBy = "";
        if (true == queryParams.getUnreadTop()) {
            // 设置未读置顶的时候，按 未读置顶 且 时间倒序 排序
            orderBy = MessageFormat.format("{0} {1}, {2} {3}", MessageConstants.COLUMN_HAS_READ, SqlConstants.ASC,
                    MessageConstants.COLUMN_CREATE_TIME, SqlConstants.DESC);
        } else {
            // 其他情况按 时间倒序 排序
            orderBy = MessageFormat.format("{0} {1}", MessageConstants.COLUMN_CREATE_TIME, SqlConstants.DESC);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("消息排序顺序：{}", orderBy);
        }
        startPage(orderBy);
        List<SysMessage> msgList = msgService.listMessageOfUser(loginUser.getUser().getId());
        Long total = new PageInfo<SysMessage>(msgList).getTotal(); // 消息总数量

        return getDataTable(msgService.do2dto(msgList), total);
    }

}
