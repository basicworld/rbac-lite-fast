# rbac-lite-fast

基于springboot的rbac权限系统后台

## API

规范：

- 只用get和post。get用于查询，post用于增删改。
- 查询列表 xxx/list   get
- 查询详情 xxx/detail get
- 新增    xxx/create post
- 删除    xxx/delete post
- 更新    xxx/update post

### 验证码类API

- 获取验证码

```
接口: /captcha
方法: get
参数: 无
返回: {code, msg, data: Captcha{uuid, base64}}
权限控制: 匿名访问
```

### 路由类API

- 判断系统可用性

```
接口: /ping
方法: get
参数: 无
返回: {code: 20000, msg: 'pong'}
权限控制: 匿名或登录后访问
```

- 获取用户的路由表

```
# 返回用户可访问的路由

接口: /router
方法: get
参数: 无
返回: {code, msg, data: [SysRouter(..., children: [SysRouter{}, ...], ...), ...]}
权限控制: 登录后访问
```

### 个人中心类API

- 用户登录

```
接口: /personal/login
方法: post
参数: {username--用户名, password--RSA加密密码, code--验证码, uuid--验证码ID}
返回: {code, msg, data: token_string}
权限控制: 匿名访问
```

- 用户修改密码

```
接口: /personal/password/update
方法: post
参数: {password--RSA加密原始密码, newPassword--RSA加密新密码}
返回: {code, msg}
权限控制: 登录后访问
```

- 用户获取个人信息

```
接口: /personal/info/detail
方法: get
参数: 无
返回: {code, msg, data: {nickname--昵称, phone, email, username, deptname}}
权限控制: 登录后访问
```

- 用户更新个人信息

```
接口: /personal/info/update
方法: post
参数: {nickName--昵称, email--邮箱, phone--手机号}
返回: {code, msg}
权限控制: 登录后访问
```

### 菜单类API

- 获取菜单列表

```
接口: /system/menu/list
方法: get
参数: {menuName--菜单名称} 可选参数，全模糊搜索
返回: {code, msg, data: [SysMenu{}, ...]}
权限控制: 登录后访问
```

- 获取菜单树

```
接口: /system/menu/tree
方法: get
参数: {menuName--菜单名称} 可选参数，全模糊搜索
返回:  {code, msg, data: [SysMenu{..., children: [SysMenu{}, ...], ...}, ...]}
权限控制: 登录后访问
```

- 获取菜单下拉选择树

```
# 用于前端的树形选择组件

接口: /system/menu/treeselect
方法: get
参数: {menuName--菜单名称} 可选参数，全模糊搜索
返回: {code, msg, data: [TreeSelect{id, label, children: [TreeSelect(), ...]}, ...]}
权限控制: 登录后访问
```

### 角色类API

- 获取角色列表

```
# 分页查询

接口: /system/role/list
方法: get
参数: {pageNum, pageSize, orderByColumn, roleKey--角色代码, roleName--角色名称} 可选参数，全模糊搜索
返回: {code, msg, total--总结果数量, rows: [SysRole{}, ...]}
权限控制: 登录后访问
```

- 获取角色详情

```
接口: /system/role/detail/{roleId}
方法: get
参数: roleId
返回: {code, msg, data: SysRole{..., menuIds: [], ...}}
权限控制: 登录后访问，需具有system:role权限
```

- 新增角色

```
接口: /system/role/create
方法: post
参数: {
    roleName--角色名称, 
    roleKey--角色代码, 
    sort--排序, 
    dataScope--数据范围，未启用的参数, 
    note--描述, 
    menuIds--关联菜单主键列表: []
}
返回: {code, msg}
权限控制: 登录后访问，需具有system:role权限
```

- 删除角色

```
接口: /system/role/delete/{roleIds}
方法: post
参数: [roleId_1, roleId_2, ...]--角色主键的列表
返回: {code, msg}
权限控制: 登录后访问，需具有system:role权限
```

- 更新角色

```
接口: /system/role/update
方法: post
参数: {id--角色主键, roleName--角色名称, roleKey--角色代码}
返回: {code, msg}
权限控制: 登录后访问，需具有system:role权限
```

### 用户类API

- 获取用户列表

```
接口: /system/user/list
方法: get
参数: {pageNum, pageSize, orderByColumn, userName, nickName, email, phone} 可选参数，全模糊查询
返回: {code, msg, total--总结果数量, rows: [SysUser{}, ...]}
权限控制: 登录后访问，需具有system:user权限
```

- 获取用户详情

```
接口: /system/user/detail/{userId}
方法: get
参数: userId
返回: {code, msg, data: SysUser{..., roleIds--用户关联角色列表: [], ...}}
权限控制: 登录后访问，需具有system:user权限
```

- 新增用户

```
接口: /system/user/create
方法: post
参数: {userName, nickName, deptName, roleIds: [], email, phone, status}
返回: {code, msg}
权限控制: 登录后访问，需具有system:user权限
```

- 删除用户

```
接口: /system/user/delete/{userIds}
方法: post
参数: [userId_1, userId_2, ...]--用户主键列表
返回: {code, msg}
权限控制: 登录后访问，需具有system:user权限
```

- 更新用户

```
接口: /system/user/update
方法: post
参数: {id, userName, nickName, deptName, roleIds: [], email, phone, status}
返回: {code, msg}
权限控制: 登录后访问，需具有system:user权限
```

- 修改用户密码

```
接口: /system/user/password/reset
方法: post
参数: {id, password--RSA加密密码}
返回: {code, msg}
权限控制: 登录后访问，需具有system:user权限
```