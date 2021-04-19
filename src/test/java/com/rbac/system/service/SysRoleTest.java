/**
 * 
 */
package com.rbac.system.service;

import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rbac.system.base.BaseTest;
import com.rbac.system.domain.SysRole;
import com.rbac.system.mapper.SysRoleMapper;

/**
 * 角色接口测试
 * 
 * @author wlfei
 * @date 2021-04-16
 */
public class SysRoleTest extends BaseTest {

	@MockBean
	private SysRoleMapper roleMapper;

	private SysRole role;

	private Long roleId = -1L;
	private String roleName = "TEST_ROLE_NAME";
	private String roleKey = "TEST_ROLE_KEY";

	@Before
	public void before() {
		role = new SysRole();
		role.setId(roleId);
		role.setRoleName(roleName);
		role.setRoleKey(roleKey);

		Mockito.when(roleMapper.selectByPrimaryKey(roleId)).thenReturn(role);
	}

	public void selectRoleByID() {
		// TODO
		// https://blog.csdn.net/hkhhkb/article/details/79789869
	}

}
