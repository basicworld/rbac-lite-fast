package com.rbac.system.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.constant.ResultConstants;
import com.rbac.common.util.BCryptUtils;
import com.rbac.common.util.RSAUtils;
import com.rbac.common.util.ServletUtils;
import com.rbac.common.util.valid.ValidUtils;
import com.rbac.framework.security.domain.LoginBody;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.SysLoginService;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.system.constant.UserConstants;
import com.rbac.system.domain.Captcha;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.dto.UserPwdDTO;
import com.rbac.system.service.ICaptchaService;
import com.rbac.system.service.ISysMessageService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

/**
 * 用户个人controller<br>
 * 登录、退出、修改密码、更新个人信息
 *
 * @author wlfei
 *
 */
@RestController
@RequestMapping("/personal")
public class SysPersonalController {
    private static final Logger logger = LoggerFactory.getLogger(SysPersonalController.class);
    // 全局rsa公钥
    @Value("${rsa.publicKey}")
    private String rsaPublicKey;
    // 全局rsa私钥
    @Value("${rsa.privateKey}")
    private String rsaPrivateKey;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    ISysRoleService roleService;

    @Autowired
    ISysUserService userService;

    @Autowired
    ISysUserRoleService userRoleService;

    @Autowired
    ICaptchaService captchaService;

    @Autowired
    ISysMessageService msgService;

    /**
     * 用户登录<br>
     *
     * 先验证验证码，验证码错误则返回错误<br>
     * 然后通过用户名+密码进行认证<br>
     * 其中入参密码是RSA加密的，需要先RSA解密<br>
     * 认证的结果包括：<br>
     * 用户已禁用、用户已锁定、用户名或密码错误、其他错误、登录成功并返回token<br>
     * <br>
     * 另外登录成功的还会判断密码过期情况，如果密码已过期，则给用户发站内信提醒（不强制修改密码，只是提醒）
     *
     * @param LoginBody [username=xxx, password=xxx, code=xxx, uuid=xxx] 登录信息
     * @return {code, msg, data=[token]}
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        logger.debug("用户登录...");

        // 验证码处置
        Captcha cap = new Captcha();
        cap.setCode(loginBody.getCode());
        cap.setUuid(loginBody.getUuid());
        if (!captchaService.validate(cap)) {
            return AjaxResult.error("无效验证码!");
        }

        // 解密前台传入的RSA加密密码，转换为密码明文
        String password = RSAUtils.decrypt(rsaPrivateKey, loginBody.getPassword());
        loginBody.setPassword(password);
        if (logger.isDebugEnabled()) {
            logger.debug("用户登录参数: {}", loginBody);
        }

        // 验证用户名和密码，并生成token
        // 验证失败时，捕获异常，打印日志
        String token = null;
        try {
            token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        } catch (DisabledException e) {
            logger.warn("用户{}已禁用!", loginBody.getUsername());
            return AjaxResult.error(ResultConstants.CODE_USER_ERROR, "用户已禁用!");
        } catch (LockedException e) {
            logger.warn("用户{}已锁定!", loginBody.getUsername());
            return AjaxResult.error(ResultConstants.CODE_USER_ERROR, "用户已锁定!");
        } catch (BadCredentialsException e) {
            logger.warn("用户名或密码错误({}, {})!", loginBody.getUsername(), loginBody.getPassword());
            return AjaxResult.error(ResultConstants.CODE_USER_ERROR, "用户名或密码错误!");
        } catch (Exception e) {
            logger.warn("登录失败({}, {})! 错误信息: {}", loginBody.getUsername(), loginBody.getPassword(), e.getMessage());
            e.printStackTrace();
            return AjaxResult.error(ResultConstants.CODE_USER_ERROR, "服务器错误(errorcode=0115)，请联系管理员!");
        }

        // 判断密码过期情况，并在密码过期时发送站内信提醒
        if (logger.isDebugEnabled()) {
            logger.debug("判断用户{}密码过期情况", loginBody.getUsername());
        }
        LoginUser loginUser = tokenService.getLoginUser(token);
        userService.checkIfPasswordExpired(loginUser.getUser(), true);

        return AjaxResult.success(ResultConstants.MESSAGE_SUCCESS, token);
    }

    /**
     * 用户修改密码<br>
     * 通过token识别用户身份
     *
     * @param userDTO {password, newPassword}
     * @return
     */
    @PostMapping("/password/update")
    public AjaxResult updatePassword(@RequestBody UserPwdDTO userDTO) {
        if (logger.isDebugEnabled()) {
            logger.debug("用户修改密码...");
        }
        // 原始密码、新密码不能为空
        if (StringUtils.isEmpty(userDTO.getNewPassword()) || StringUtils.isEmpty(userDTO.getPassword())) {
            return AjaxResult.error("原密码和新密码不能为空!");
        }
        // 解密前台传入的RSA加密的原始密码
        String oldPassword = RSAUtils.decrypt(rsaPrivateKey, userDTO.getPassword());
        // 解密前台传入的RSA加密的新密码
        String newPassword = RSAUtils.decrypt(rsaPrivateKey, userDTO.getNewPassword());

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 从数据库获取用户信息
        SysUser userInDB = userService.selectByPrimaryKey(loginUser.getUser().getId());
        // 检查原始密码是否正确
        boolean isOldPasswordOk = BCryptUtils.isSamePassword(oldPassword, userInDB.getPassword());
        if (!isOldPasswordOk) {
            return AjaxResult.error("原密码错误!");
        }
        // 新密码加密存储
        String encodeNewPassword = BCryptUtils.encode(newPassword);

        // 根据用户ID更新密码
        SysUser user = new SysUser();
        user.setId(loginUser.getUser().getId());
        user.setPassword(encodeNewPassword);
        user.setPwdUpdateTime(new Date());
        userService.updatePasswordByPrimaryKey(user);

        // 站内信通知
        msgService.insertPersonalChangePasswordMessage(userInDB.getNickName(), userInDB.getId());
        if (logger.isWarnEnabled()) {
            logger.info("用户{}修改密码成功", userInDB.getUserName());
        }

        return AjaxResult.success();
    }

    /**
     * 用户更新个人信息<br>
     * 更新成功后发送站内信通知<br>
     * 通过token识别用户身份
     *
     * @param user{nickname, email, phone} 允许更新昵称、邮箱、手机号字段
     * @return
     */
    @PostMapping("/info/update")
    public AjaxResult updateInfo(@RequestBody SysUser user) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        logger.debug("用户(id={}) 更新个人信息...", loginUser.getUser().getId());

        // 用户可更新的字段：昵称、邮箱、手机号
        SysUser updateUser = new SysUser();
        updateUser.setId(loginUser.getUser().getId());
        updateUser.setNickName(StringUtils.defaultString(user.getNickName(), ""));
        updateUser.setEmail(StringUtils.defaultString(user.getEmail(), ""));
        updateUser.setPhone(StringUtils.defaultString(user.getPhone(), ""));
        // 昵称格式校验
        boolean validNickName = ValidUtils.isLengthBetween(updateUser.getNickName(), UserConstants.NICKNAME_MIN_LEN,
                UserConstants.NICKNAME_MAX_LEN);
        if (true != validNickName) {
            String msg = MessageFormat.format("昵称长度限制为{0}-{1}个字符", UserConstants.NICKNAME_MIN_LEN,
                    UserConstants.NICKNAME_MAX_LEN - 1);
            return AjaxResult.error(msg);
        }
        // 邮箱格式校验
        if (StringUtils.isNotBlank(updateUser.getEmail())) {
            boolean emailValid = ValidUtils.isValidEmail(updateUser.getEmail());
            if (true != emailValid) {
                logger.warn("邮箱格式错误:{}", updateUser.getEmail());
                return AjaxResult.error("邮箱格式错误！");
            }
        }
        // 手机号格式校验
        if (StringUtils.isNotBlank(updateUser.getPhone())) {
            boolean phoneValid = ValidUtils.isValidPhone(updateUser.getPhone());
            if (true != phoneValid) {
                logger.warn("手机号格式错误:{}", updateUser.getPhone());
                return AjaxResult.error("手机号格式错误！");
            }
        }

        // 更新非null字段
        userService.updatePersonalInfoSelective(updateUser);

        // 站内信通知
        msgService.insertPersonalChangeInfoMessage(updateUser.getNickName(), updateUser.getId());

        return AjaxResult.success();
    }

    /**
     * 用户获取个人信息详情<br>
     * 从数据库获取数据，不从redis缓存获取数据，避免缓存更新不及时问题（缓存一般在登录的时候更新）<br>
     * 返回角色列表时，如果没有任何角色，则返回一个特殊标记的空角色（__empty__），避免前端获取不到角色的异常
     *
     * @return {username, roles[], nickname, phone, email, deptname}
     */
    @GetMapping("/info/detail")
    public AjaxResult getInfo() {
        logger.debug("用户获取个人信息详情...");
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 从数据库获取用户的所有角色代码（角色标识符）
        List<String> roles = userRoleService.listByUserId(user.getId()).stream()
                .map(v -> roleService.selectByPrimaryKey(v.getRoleId()).getRoleKey()).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(roles)) {
            logger.warn("用户(id={}) 不具备任何角色", user.getId());
            // 为用户添加一个空角色代码标识，解决前端框架的异常
            roles.add("__empty__");
        }
        // 构造返回值
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("roles", roles);
        // 从数据库获取用户信息反馈
        SysUser userInDB = userService.selectByPrimaryKey(user.getId());
        data.put("nickname", userInDB.getNickName());
        data.put("phone", userInDB.getPhone());
        data.put("email", userInDB.getEmail());
        data.put("username", userInDB.getUserName());
        data.put("deptname", userInDB.getDeptName());

        return AjaxResult.success(data);
    }

}
