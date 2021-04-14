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
参数: 无
返回: {code, msg, data: token_string}
权限控制: 匿名访问
```

- 用户修改密码

```
接口: /personal/password/update
方法: post
参数: 无
返回: {code, msg}
权限控制: 登录后访问
```

- 用户获取个人信息

```
接口: /personal/info/detail
方法: get
参数: 无
返回: {code, msg, data: {nickname, phone, email, username, deptname}}
权限控制: 登录后访问
```

- 用户更新个人信息

```
接口: /personal/info/update
方法: post
参数: 无
返回: {code, msg}
权限控制: 登录后访问
```

### 菜单类API

- 获取菜单列表

```
接口: /system/menu/list
方法: get
参数: 可选参数SysMenu{menuName,}
返回: {code, msg, data: [SysMenu{}, ...]}
权限控制: 登录后访问
```

- 获取菜单树

```
接口: /system/menu/tree
方法: get
参数: 可选参数SysMenu{menuName,}
返回:  {code, msg, data: [SysMenu{..., children: [SysMenu{}, ...], ...}, ...]}
权限控制: 登录后访问
```

- 获取菜单下拉选择树

```
# 用于前端的树形选择组件

接口: /system/menu/treeselect
方法: get
参数: 可选参数SysMenu{menuName,}
返回: {code, msg, data: [TreeSelect{id, label, children: [TreeSelect(), ...]}, ...]}
权限控制: 登录后访问
```

### 角色类API

- 获取角色列表

```
接口: /system/role/list
方法: get
参数: 可选参数SysRole{}
返回: {code, msg, data: [SysRole{}, ...]}
权限控制: 登录后访问
```

- 获取角色详情

```
接口: /system/role/detail/{roleId}
方法: get
参数: roleId
返回: {code, msg, data: SysRole{..., menuIds: [], ...}}
权限控制: 登录后访问
```

- 新增角色

```
接口: /system/role/create
方法: post
参数: SysRole{}
返回: {code, msg}
权限控制: 登录后访问
```

- 删除角色

```
接口: /system/role/delete/{roleIds}
方法: post
参数: [roleId_1, roleId_2, ...]
返回: {code, msg}
权限控制: 登录后访问
```

- 更新角色

```
接口: /system/role/update
方法: post
参数: SysRole{}
返回: {code, msg}
权限控制: 登录后访问
```

### 用户类API

- 获取用户列表

```
接口: /system/user/list
方法: get
参数: 可选参数SysUser{}
返回: {code, msg, data: [SysUser{}, ...]}
权限控制: 登录后访问
```

- 获取用户详情

```
接口: /system/user/detail/{userId}
方法: get
参数: userId
返回: {code, msg, data: SysUser{..., roleIds: [], ...}}
权限控制: 登录后访问
```

- 新增用户

```
接口: /system/user/create
方法: post
参数: SysUser{}
返回: {code, msg}
权限控制: 登录后访问
```

- 删除角色

```
接口: /system/user/delete/{userIds}
方法: post
参数: [userId_1, userId_2, ...]
返回: {code, msg}
权限控制: 登录后访问
```

- 更新用户

```
接口: /system/user/update
方法: post
参数: SysUser{}
返回: {code, msg}
权限控制: 登录后访问
```

- 修改用户密码

```
接口: /system/user/password/reset
方法: post
参数: SysUser{}
返回: {code, msg}
权限控制: 登录后访问
```