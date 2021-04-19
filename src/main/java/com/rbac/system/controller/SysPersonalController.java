package com.rbac.system.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbac.common.constant.ResultConstants;
import com.rbac.common.util.BCryptUtils;
import com.rbac.common.util.DateUtils;
import com.rbac.common.util.RSAUtils;
import com.rbac.common.util.ServletUtils;
import com.rbac.framework.security.domain.LoginBody;
import com.rbac.framework.security.domain.LoginUser;
import com.rbac.framework.security.service.SysLoginService;
import com.rbac.framework.security.service.TokenService;
import com.rbac.framework.web.domain.AjaxResult;
import com.rbac.system.domain.Captcha;
import com.rbac.system.domain.SysUser;
import com.rbac.system.domain.dto.UserPwdDTO;
import com.rbac.system.service.ICaptchaService;
import com.rbac.system.service.ISysRoleService;
import com.rbac.system.service.ISysUserRoleService;
import com.rbac.system.service.ISysUserService;

/**
 * 用户个人API<br>
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

	/**
	 * 用户登录
	 * 
	 * @param loginBody 登录信息
	 * @return
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

		logger.debug("用户登录参数: {}", loginBody);

		// 验证用户名和密码，并生成token
		String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());

		return AjaxResult.success(ResultConstants.MESSAGE_SUCCESS, token);
	}

	/**
	 * 用户修改密码
	 * 
	 * @param userDTO {password, newPassword}
	 * @return
	 */
	@PostMapping("/password/update")
	public AjaxResult updatePassword(@RequestBody UserPwdDTO userDTO) {
		logger.debug("用户重置密码...");
		if (StringUtils.isEmpty(userDTO.getNewPassword()) || StringUtils.isEmpty(userDTO.getPassword())) {
			return AjaxResult.error("原密码和新密码不能为空!");
		}
		// 解密前台传入的RSA加密 原始密码
		String oldPassword = RSAUtils.decrypt(rsaPrivateKey, userDTO.getPassword());
		// 解密前台传入的RSA加密 新密码
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
		user.setPwdUpdateTime(DateUtils.getNowDate());
		userService.updatePasswordByPrimaryKey(user);

		return AjaxResult.success();
	}

	/**
	 * 用户更新个人信息
	 * 
	 * @param user{nickname, email, phone}
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

		userService.updateSelective(updateUser);

		return AjaxResult.success();
	}

	/**
	 * 用户获取个人信息详情
	 * 
	 * @return {username, roles[], nickname, phone, email, deptname}
	 */
	@GetMapping("/info/detail")
	public AjaxResult getInfo() {
		logger.debug("用户获取个人信息详情...");
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		// 获取用户的所有角色代码
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
