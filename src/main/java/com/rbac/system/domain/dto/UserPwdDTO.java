package com.rbac.system.domain.dto;

/**
 * 用户密码DTO<br>
 * 修改密码用
 * 
 * @author wlfei
 *
 */
public class UserPwdDTO {
    // old password
    private String password;

    // new password
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserPwdDTO [password=" + password + ", newPassword=" + newPassword + "]";
    }

}
